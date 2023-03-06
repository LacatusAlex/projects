#version 410 core

in vec3 fPosition;
in vec3 fNormal;
in vec2 fTexCoords;
in vec4 fPosEye;

out vec4 fColor;

//matrices
uniform mat4 model2;
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


void computeDirLight()
{
    //compute eye space coordinates
    vec4 fPosEye = view * model2 * vec4(fPosition, 1.0f);
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

void main() 
{
    computeDirLight();
	

    //compute final vertex color
    vec3 color = min((ambient + diffuse) * texture(diffuseTexture, fTexCoords).rgb + specular * texture(specularTexture, fTexCoords).rgb, 1.0f);
	
	computeLightComponents();

	vec3 color2 = min((ambient2 + diffuse2) * texture(diffuseTexture, fTexCoords).rgb + specular2 * texture(specularTexture, fTexCoords).rgb, 1.0f);
	
    fColor = vec4(color+color2, 1.0f);
	//fColor = vec4(color2, 1.0f);
}
