#version 410 core

in vec3 fPosition;
in vec3 fNormal;
in vec2 fTexCoords;
in vec4 fPosEye;
in vec4 fragPosLightSpace;

out vec4 fColor;

//matrices
uniform mat4 model;
uniform mat4 view;
uniform mat3 normalMatrix;
//lighting
uniform vec3 lightDir;
uniform vec3 lightColor;

uniform vec3 lightPosEye;
uniform vec3 lightColor2;
// textures
uniform sampler2D diffuseTexture;
uniform sampler2D specularTexture;
uniform sampler2D shadowMap;


//components
vec3 ambient;
float ambientStrength = 0.2f;
vec3 diffuse;
vec3 specular;
float specularStrength = 0.5f;


vec3 ambient2;

vec3 diffuse2;
vec3 specular2;
float shininess2 = 32.0f;

float constant = 1.0f;
float linear = 0.0045f;
float quadratic = 0.0075f;


float computeShadow()
{

	//perform perspective divide
	vec3 normalizedCoords= fragPosLightSpace.xyz / fragPosLightSpace.w;

	//tranform from [-1,1] range to [0,1] range
	normalizedCoords = normalizedCoords * 0.5 + 0.5;

	//get closest depth value from lights perspective
	float closestDepth = texture(shadowMap, normalizedCoords.xy).r;

	//get depth of current fragment from lights perspective
	float currentDepth = normalizedCoords.z;

	//if the current fragments depth is greater than the value in the depth map, the current fragment is in shadow 
	//else it is illuminated
	//float shadow = currentDepth > closestDepth ? 1.0 : 0.0;
	float bias = 0.005f;
	float shadow = currentDepth - bias > closestDepth ? 1.0 : 0.0;
	if (normalizedCoords.z > 1.0f)
		return 0.0f;
	return shadow;
	
	


}


void computeDirLight()
{
    //compute eye space coordinates
    vec4 fPosEye = view * model * vec4(fPosition, 1.0f);
    vec3 normalEye = normalize(normalMatrix * fNormal);

    //normalize light direction
    vec3 lightDirN = vec3(normalize(view * vec4(lightDir, 0.0f)));

    //compute view direction (in eye coordinates, the viewer is situated at the origin
    vec3 viewDir = normalize(- fPosEye.xyz);

    //compute ambient light
    ambient = ambientStrength * lightColor;

    //compute diffuse light
    diffuse = max(dot(normalEye, lightDirN), 0.0f) * lightColor;

    //compute specular light
    vec3 reflectDir = reflect(-lightDirN, normalEye);
    float specCoeff = pow(max(dot(viewDir, reflectDir), 0.0f), 32);
    specular = specularStrength * specCoeff * lightColor;
}


void computeLightComponents()
{		
	vec3 cameraPosEye = vec3(0.0f);//in eye coordinates, the viewer is situated at the origin
	
	//transform normal
	vec3 normalEye = normalize(fNormal);	
	
	//compute light direction
	vec3 lightDirN = normalize(lightPosEye - fPosEye.xyz);
	
	//compute view direction
	vec3 viewDirN = normalize(- fPosEye.xyz);
	
	//compute half vector
	vec3 halfVector = normalize(lightDirN + viewDirN);

	
	//compute distance to light
	float dist = length(lightPosEye - fPosEye.xyz);
	//compute attenuation
	float att = 1.0f / (constant + linear * dist + quadratic * (dist * dist));
	//compute ambient light
	ambient2 = att * ambientStrength * lightColor2;
	//compute diffuse light
	diffuse2 = att * max(dot(normalEye, lightDirN), 0.0f) * lightColor2;
	float specCoeff = pow(max(dot(normalEye, halfVector), 0.0f), shininess2);
	specular2 = att * specularStrength * specCoeff * lightColor2;


}
float computeFog()
{
 vec4 fPosEye = view * model * vec4(fPosition, 1.0f);
 float fogDensity = 0.05f;
 float fragmentDistance = length(fPosEye.xyz);
 float fogFactor = exp(-pow(fragmentDistance * fogDensity, 2));
 
 return clamp(fogFactor, 0.0f, 1.0f);
}

void main() 
{
	float shadow = computeShadow();
    computeDirLight();
	

    //compute final vertex color
    vec3 color = min((ambient + (1.0f - shadow) *diffuse) * texture(diffuseTexture, fTexCoords).rgb + (1.0f - shadow) *specular * texture(specularTexture, fTexCoords).rgb, 1.0f);
	
	computeLightComponents();

	vec3 color2 = min((ambient2 + (1.0f - shadow) *diffuse2) * texture(diffuseTexture, fTexCoords).rgb + (1.0f - shadow) *specular2 * texture(specularTexture, fTexCoords).rgb, 1.0f);
	
    //fColor = vec4(color+color2, 1.0f);
	vec4 coloraux= vec4(color+color2, 1.0f);
	//fColor = vec4(color2, 1.0f);
	float fogFactor = computeFog();
	vec4 fogColor = vec4(0.5f, 0.5f, 0.5f, 1.0f);
	fColor = mix(fogColor, coloraux, fogFactor);
	//fColor = fogColor * (1 - fogFactor) + coloraux * fogFactor;

}
