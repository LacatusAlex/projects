#define GLEW_STATIC
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp> //core glm functionality
#include <glm/gtc/matrix_transform.hpp> //glm extension for generating common transformation matrices
#include <glm/gtc/matrix_inverse.hpp> //glm extension for computing inverse matrices
#include <glm/gtc/type_ptr.hpp> //glm extension for accessing the internal data structure of glm types

#include "Window.h"
#include "Shader.hpp"
#include "Camera.hpp"
#include "Model3D.hpp"
#include "SkyBox.hpp"

#include <iostream>
#include <cstdlib>

// window
gps::Window myWindow;

const unsigned int SHADOW_WIDTH = 20480;
const unsigned int SHADOW_HEIGHT = 20480;


// matrices
glm::mat4 model;
glm::mat4 model2;
glm::mat4 view;
glm::mat4 projection;
glm::mat3 normalMatrix;
glm::mat4 lightRotation;

// light parameters
glm::vec3 lightDir;
glm::vec3 lightColor;

glm::vec3 lightDir2;
glm::vec3 lightColor2;

// shader uniform locations
GLint modelLoc;
GLint modelLoc2;
GLint viewLoc;
GLint projectionLoc;
GLint normalMatrixLoc;
GLint lightDirLoc;
GLint lightColorLoc;

glm::vec3 lightPos;
GLuint lightPosLoc;
GLfloat lightAngle;


GLint lightDirLoc2;
GLint lightColorLoc2;

// camera
gps::Camera myCamera(
    glm::vec3(50.0f, 0.0f, 60.0f),
    glm::vec3(0.0f, 0.0f, -10.0f),
    glm::vec3(0.0f, 1.0f, 0.0f));

GLfloat cameraSpeed = 0.1f;

GLboolean pressedKeys[1024];

// models
gps::Model3D nanosuit;

gps::Model3D ground[11][11];
gps::Model3D Obj[11][11];
gps::Model3D towers[100];
int totalTowers = 0;

int objd[11][11];
gps::Model3D house;
gps::Model3D lightCube;
gps::Model3D screenQuad;
GLfloat angle;
GLfloat angleOfLight;

// shaders
gps::Shader myBasicShader;
gps::Shader depthMapShader;

GLuint shadowMapFBO;
GLuint depthMapTexture;
GLuint textureID;

std::vector<const GLchar*> faces;

gps::SkyBox mySkyBox;
gps::Shader skyboxShader;

bool lightOn = true;
bool wireRepr = false;

GLfloat nanox = 0.0f;
GLfloat nanoz = 0.0f;
GLfloat nanoscale = 0.0f;




GLenum glCheckError_(const char* file, int line)
{
    GLenum errorCode;
    while ((errorCode = glGetError()) != GL_NO_ERROR) {
        std::string error;
        switch (errorCode) {
        case GL_INVALID_ENUM:
            error = "INVALID_ENUM";
            break;
        case GL_INVALID_VALUE:
            error = "INVALID_VALUE";
            break;
        case GL_INVALID_OPERATION:
            error = "INVALID_OPERATION";
            break;
        case GL_STACK_OVERFLOW:
            error = "STACK_OVERFLOW";
            break;
        case GL_STACK_UNDERFLOW:
            error = "STACK_UNDERFLOW";
            break;
        case GL_OUT_OF_MEMORY:
            error = "OUT_OF_MEMORY";
            break;
        case GL_INVALID_FRAMEBUFFER_OPERATION:
            error = "INVALID_FRAMEBUFFER_OPERATION";
            break;
        }
        std::cout << error << " | " << file << " (" << line << ")" << std::endl;
    }
    return errorCode;
}
#define glCheckError() glCheckError_(__FILE__, __LINE__)




void windowResizeCallback(GLFWwindow* window, int width, int height) {
    fprintf(stdout, "Window resized! New width: %d , and height: %d\n", width, height);
    //TODO
}

void keyboardCallback(GLFWwindow* window, int key, int scancode, int action, int mode) {
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(window, GL_TRUE);
    }

    if (key >= 0 && key < 1024) {
        if (action == GLFW_PRESS) {
            pressedKeys[key] = true;
        }
        else if (action == GLFW_RELEASE) {
            pressedKeys[key] = false;
        }
    }
}

float xm = 0;
float ym = 0;
float x = 50.0f;
float y = 0.0f;
float z = 60.0f;
void mouseCallback(GLFWwindow* window, double xpos, double ypos) {

    glfwGetCursorPos(window, &xpos, &ypos);
    xm = ((float)xpos - 500) / 20;
    ym = ((float)ypos - 500) / 20;
    view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
    myBasicShader.useShaderProgram();
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
    printf("%f\n", xpos);
    // compute normal matrix for teapot
    //model = glm::translate(model, glm::vec3(0, 0, cameraSpeed));
    normalMatrix = glm::mat3(glm::inverseTranspose(view * model));





}

float deltaTime = 0.0f;	// Time between current frame and last frame
float lastFrame = 0.0f; // Time of last frame


GLfloat incr = 0.1f;
GLfloat propAngle = 0.0f;
void processMovement() {
    propAngle += 1.0f;
    if (angleOfLight > 90.0f)incr = -0.1f;
    if (angleOfLight < -90.0f)incr = 0.1f;
    angleOfLight += incr;

    if (pressedKeys[GLFW_KEY_W]) {
        
        nanoz -= 0.1f;

        //update view matrix
        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));

        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        model = glm::translate(model, glm::vec3(0, 0, cameraSpeed));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        


    }

    if (pressedKeys[GLFW_KEY_S]) {

        nanoz += 0.1f;

        //update view matrix


        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        model = glm::translate(model, glm::vec3(0, 0, -cameraSpeed));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));


    }

    if (pressedKeys[GLFW_KEY_A]) {
        nanox -= 0.1f;

        //update view matrix
        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        model = glm::translate(model, glm::vec3(-cameraSpeed, 0, 0));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
        

    }

    if (pressedKeys[GLFW_KEY_D]) {
        nanox += 0.1f;

        //update view matrix
        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        model = glm::translate(model, glm::vec3(cameraSpeed, 0, 0));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

    }

    if (pressedKeys[GLFW_KEY_Z]) {
        nanoscale += 0.1f;

        //update view matrix
        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        model = glm::translate(model, glm::vec3(cameraSpeed, 0, 0));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

    }

    if (pressedKeys[GLFW_KEY_X]) {
        nanoscale += -0.1f;

        //update view matrix
        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        model = glm::translate(model, glm::vec3(cameraSpeed, 0, 0));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

    }



    if (pressedKeys[GLFW_KEY_Y]) {

        myCamera.move(gps::MOVE_FORWARD, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        z -= cameraSpeed;

        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot
        //model = glm::translate(model, glm::vec3(0, 0, cameraSpeed));
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));


    }

    if (pressedKeys[GLFW_KEY_H]) {
        myCamera.move(gps::MOVE_BACKWARD, cameraSpeed);
        //update view matrix
        view = myCamera.getViewMatrix();
        z += cameraSpeed;

        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));

        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));


    }

    if (pressedKeys[GLFW_KEY_G]) {
        myCamera.move(gps::MOVE_LEFT, cameraSpeed);
        //update view matrix
        x -= cameraSpeed;

        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot

        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

    }

    if (pressedKeys[GLFW_KEY_J]) {
        myCamera.move(gps::MOVE_RIGHT, cameraSpeed);
        //update view matrix
        x += cameraSpeed;

        view = glm::lookAt(glm::vec3(x, y, z), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0, 1, 0));
        myBasicShader.useShaderProgram();
        glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));
        // compute normal matrix for teapot

        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));

    }

    if (pressedKeys[GLFW_KEY_Q]) {
        angle += -1.0f;
        // update model matrix for teapot
        
    }

    if (pressedKeys[GLFW_KEY_E]) {
        angle += 1.0f;
        // update model matrix for teapot
        
    }
    if (pressedKeys[GLFW_KEY_O]) {
        
            myBasicShader.useShaderProgram();
            lightColor2 = glm::vec3(0.0f, 0.0f, 0.0f); //white light
            lightColorLoc2 = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor2");
            // send light color to shader
            glUniform3fv(lightColorLoc2, 1, glm::value_ptr(lightColor2));
        

    }

    if (pressedKeys[GLFW_KEY_P]) {
        myBasicShader.useShaderProgram();
        lightColor2 = glm::vec3(1.0f, 0.0f, 0.0f); //white light
        lightColorLoc2 = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor2");
        // send light color to shader
        glUniform3fv(lightColorLoc2, 1, glm::value_ptr(lightColor2));

    }


    if (pressedKeys[GLFW_KEY_C]) {
        
         glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        

    }
    if (pressedKeys[GLFW_KEY_V]) {

        glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);


    }
    if (pressedKeys[GLFW_KEY_B]) {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

    }



    /*if (pressedKeys[GLFW_KEY_O]) {
        float scale = 1.05f;
        // update model matrix for teapot
        model = glm::scale(model, glm::vec3(scale, scale, scale));
        // update normal matrix for teapot
        normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    }*/

   
}

void initOpenGLWindow() {
    myWindow.Create(1024, 768, "OpenGL Project Core");
}

void setWindowCallbacks() {
    glfwSetWindowSizeCallback(myWindow.getWindow(), windowResizeCallback);
    glfwSetKeyCallback(myWindow.getWindow(), keyboardCallback);
    glfwSetCursorPosCallback(myWindow.getWindow(), mouseCallback);
}

void initOpenGLState() {
    glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
    glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
    glEnable(GL_FRAMEBUFFER_SRGB);
    glEnable(GL_DEPTH_TEST); // enable depth-testing
    glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
    glEnable(GL_CULL_FACE); // cull face
    glCullFace(GL_BACK); // cull back face
    glFrontFace(GL_CCW); // GL_CCW for counter clock-wise
}

void initModels() {
    house.LoadModel("objects/casabosque/casabosque.obj");
    for (int i = 0;i < 10;i++) {
        for (int j = 0;j < 10;j++) {
            ground[i][j].LoadModel("objects/ground/ground.obj");
        }
    }
    srand(time(0));
    int x;
    
    int ind = 0;
    for (int i = 0;i < 10;i++) {
        for (int j = 0;j < 10;j++) {
            x = rand() % 6;
            if (i == 0 && j == 0) continue;

            switch (x)
            {
            case 0:
                Obj[i][j].LoadModel("objects/trees_9_obj/tree1.obj");

                break;
            case 1:
                Obj[i][j].LoadModel("objects/Tree2/Tree.obj");
                break;
            case 2:
                Obj[i][j].LoadModel("objects/rock/rock_v2.obj");
                break;
            case 3:
                Obj[i][j].LoadModel("objects/hedge/10453_Round_Box_Hedge_v1_Iteration3.obj");
                break;
            case 4:
                Obj[i][j].LoadModel("objects/propeller/prop.obj");
                towers[ind++].LoadModel("objects/tower/10092_Water Tower_SG_V1_Iterations-2.obj");
                break;
            

            default:
                //Obj[i][j].LoadModel("objects/propeller/15532_P-38EJL_Lightning_Propeller_V1_new.obj");
                break;
            }
            objd[i][j] = x;
            
        }
    }
    
    nanosuit.LoadModel("objects/nanosuit/nanosuit.obj");
    
}

void initShaders() {
    myBasicShader.loadShader(
        "shaders/basic.vert",
        "shaders/basic.frag");
    myBasicShader.useShaderProgram();
    depthMapShader.loadShader("shaders/depthMapShader.vert", "shaders/depthMapShader.frag");
    depthMapShader.useShaderProgram();

    mySkyBox.Load(faces);
    skyboxShader.loadShader("shaders/skyboxShader.vert", "shaders/skyboxShader.frag");
    skyboxShader.useShaderProgram();

    view = myCamera.getViewMatrix();
    glUniformMatrix4fv(glGetUniformLocation(skyboxShader.shaderProgram, "view"), 1, GL_FALSE,
        glm::value_ptr(view));

    projection = glm::perspective(glm::radians(45.0f), (float)myWindow.getWindowDimensions().width / myWindow.getWindowDimensions().height, 0.1f, 1000.0f);
    glUniformMatrix4fv(glGetUniformLocation(skyboxShader.shaderProgram, "projection"), 1, GL_FALSE,
        glm::value_ptr(projection));



}

void initUniforms() {
    myBasicShader.useShaderProgram();

    // create model matrix for teapot
    model = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0.0f, 1.0f, 0.0f));
    modelLoc = glGetUniformLocation(myBasicShader.shaderProgram, "model");

    // get view matrix for current camera
    view = myCamera.getViewMatrix();
    viewLoc = glGetUniformLocation(myBasicShader.shaderProgram, "view");
    // send view matrix to shader
    glUniformMatrix4fv(viewLoc, 1, GL_FALSE, glm::value_ptr(view));

    // compute normal matrix for teapot
    normalMatrix = glm::mat3(glm::inverseTranspose(view * model));
    normalMatrixLoc = glGetUniformLocation(myBasicShader.shaderProgram, "normalMatrix");

    // create projection matrix
    projection = glm::perspective(glm::radians(45.0f),
        (float)myWindow.getWindowDimensions().width / (float)myWindow.getWindowDimensions().height,
        0.1f, 100.0f);
    projectionLoc = glGetUniformLocation(myBasicShader.shaderProgram, "projection");
    // send projection matrix to shader
    glUniformMatrix4fv(projectionLoc, 1, GL_FALSE, glm::value_ptr(projection));

    //set the light direction (direction towards the light)
    lightDir = glm::vec3(0.0f, 1.0f, 1.0f);
    lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(angleOfLight), glm::vec3(1.0f, 0.0f, 0.0f));
    lightDirLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightDir");
    glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation))*lightDir));

    //set light color
    lightColor = glm::vec3(1.0f, 1.0f, 1.0f); //yellow light
    lightColorLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor");
    // send light color to shader
    glUniform3fv(lightColorLoc, 1, glm::value_ptr(lightColor));


    lightPos = glm::vec3(2.0f, 5.0f, 1.0f);
    lightPosLoc = glGetUniformLocation(myBasicShader.shaderProgram, "lightPosEye");
    glUniform3fv(lightPosLoc, 1, glm::value_ptr(glm::mat3(view) * lightPos));

    //set light color
    lightColor2 = glm::vec3(1.0f, 0.0f, 0.0f); //white light
    lightColorLoc2 = glGetUniformLocation(myBasicShader.shaderProgram, "lightColor2");
    // send light color to shader
    glUniform3fv(lightColorLoc2, 1, glm::value_ptr(lightColor2));


}

bool initCubeBox() {
   /* faces.push_back("skybox/cloudtop_rt.tga");
    faces.push_back("skybox/cloudtop_lf.tga");
    faces.push_back("skybox/cloudtop_up.tga");
    faces.push_back("skybox/cloudtop_dn.tga");
    faces.push_back("skybox/cloudtop_bk.tga");
    faces.push_back("skybox/cloudtop_ft.tga");*/

    faces.push_back("skybox/right.tga");
    faces.push_back("skybox/left.tga");
    faces.push_back("skybox/top.tga");
    faces.push_back("skybox/bottom.tga");
    faces.push_back("skybox/back.tga");
    faces.push_back("skybox/front.tga");

    
    int width, height, n;
    for (GLuint i = 0; i < faces.size(); i++)
    {
        unsigned char *image = stbi_load(faces[i], &width, &height, &n,0);//
        if (!image) {
            fprintf(stderr, "ERROR: could not load %s\n", faces[i]);
            return false;
        }
        glTexImage2D(
            GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0,
            GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
    }
    



    

}




void initFBO() {
    //TODO - Create the FBO, the depth texture and attach the depth texture to the FBO
    //generate FBO ID
    glGenFramebuffers(1, &shadowMapFBO);

    //create depth texture for FBO
    glGenTextures(1, &depthMapTexture);
    glBindTexture(GL_TEXTURE_2D, depthMapTexture);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);

    //attach texture to FBO
    glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMapTexture, 0);

    //bind nothing to attachment points
    glDrawBuffer(GL_NONE);
    glReadBuffer(GL_NONE);

    //unbind until ready to use
    glBindFramebuffer(GL_FRAMEBUFFER, 0);

}

glm::mat4 computeLightSpaceTrMatrix() {
    //TODO - Return the light-space transformation matrix
    //return glm::mat4(1.0f);
    //glm::mat4 lightView = glm::lookAt(4.0f*lightDir+glm::vec3(x , y , z ), glm::vec3(x + xm, y - ym, z - 10), glm::vec3(0.0f, 1.0f, 0.0f));
    glm::mat4 lightView = glm::lookAt(glm::mat3(lightRotation)* lightDir + glm::vec3(x ,y,z), glm::vec3(x, y, z), glm::vec3(0.0f, 1.0f, 0.0f));
    
    const GLfloat near_plane = -5.0f, far_plane = 50.0f;
    glm::mat4 lightProjection = glm::ortho(-150.0f,150.0f, -150.0f, 150.0f, near_plane, far_plane);
    glm::mat4 lightSpaceTrMatrix = lightProjection * lightView;

    return lightSpaceTrMatrix;



}

void drawObjects(gps::Shader shader, bool depthPass) {

    shader.useShaderProgram();
    //angle += 1.0f;
    // update model matrix for teapot
    
    model = glm::translate(glm::mat4(1.0f), glm::vec3(nanox, 0.0f, nanoz));
    model = glm::scale(model, glm::vec3(1.0f+nanoscale));
    model = glm::rotate(model, glm::radians(angle), glm::vec3(0, 1, 0));
    
    modelLoc = glGetUniformLocation(shader.shaderProgram, "model");
    
    //send teapot model matrix data to shader
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

    //send teapot normal matrix data to shader
    if (!depthPass) {
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }

    nanosuit.Draw(shader);
    //wolf.Draw(shader);



    int ind = 0;
    for (int i = 0;i < 10;i++) {
        for (int j = 0;j < 10;j++) {

            model = glm::translate(glm::mat4(1.0f), glm::vec3(-20 + 10 * i, -1.0f, -20 + 10 * j));

            switch (objd[i][j]) {
            case 0:
                model = glm::scale(model, glm::vec3(0.5000f));
                break;
            case 1:
                model = glm::scale(model, glm::vec3(1.5000f));
                break;
            case 2:
                model = glm::scale(model, glm::vec3(0.1500f));
                break;
            case 3:
                model = glm::translate(model, glm::vec3(0.0f, 0.5f, 0.0f));

                model = glm::scale(model, glm::vec3(0.01500f));
                break;
            case 4:
                model = glm::scale(model, glm::vec3(0.001500f));
                model = glm::rotate(model, glm::radians(-90.0f), glm::vec3(1.0f, 0.0f, 0.0f));

                modelLoc = glGetUniformLocation(shader.shaderProgram, "model");
                glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

                //send teapot normal matrix data to shader
                if (!depthPass) {
                    glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
                }

                // draw teapot


                towers[ind].Draw(shader);
                
                model = glm::rotate(model, glm::radians(90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
                model = glm::scale(model, glm::vec3(1/0.001500f));
                //model = glm::translate(model, glm::vec3(0.0f, 0.5f, 0.0f));
                model = glm::translate(model, glm::vec3(0.0f, 4.5f, 0.8f));
                model = glm::scale(model, glm::vec3(0.02200f));
                model = glm::rotate(model, glm::radians(-90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
                model = glm::rotate(model, glm::radians(propAngle), glm::vec3(0.0f, 1.0f, 0.0f));
                
                break;


            }

            
            //model = glm::rotate(glm::mat4(1.0f), glm::radians(0.0f), glm::vec3(1.0f, 0.0f, 0.0f));

            
            modelLoc = glGetUniformLocation(shader.shaderProgram, "model");
            glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

            //send teapot normal matrix data to shader
            if (!depthPass) {
                glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
            }

            // draw teapot


            Obj[i][j].Draw(shader);

        }
    }

    //send teapot model matrix data to shader
    for (int i = 0;i < 10;i++) {
        for (int j = 0;j < 10;j++) {
            model = glm::translate(glm::mat4(1.0f), glm::vec3(-20 + 10 * i, -1.0f,-20 + 10 * j));
            

            model = glm::scale(model, glm::vec3(0.5f));
            
            modelLoc = glGetUniformLocation(shader.shaderProgram, "model");
            glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

            //send teapot normal matrix data to shader
            if (!depthPass) {
                glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
            }

            // draw teapot


            ground[i][j].Draw(shader);

        }
    }

    model = glm::translate(glm::mat4(1.0f), glm::vec3(-10, -1.1f, -10));
    //model = glm::rotate(glm::mat4(1.0f), glm::radians(0.0f), glm::vec3(1.0f, 0.0f, 0.0f));

    model = glm::scale(model, glm::vec3(0.0100f));
    modelLoc = glGetUniformLocation(shader.shaderProgram, "model");
    glUniformMatrix4fv(modelLoc, 1, GL_FALSE, glm::value_ptr(model));

    //send teapot normal matrix data to shader
    if (!depthPass) {
        glUniformMatrix3fv(normalMatrixLoc, 1, GL_FALSE, glm::value_ptr(normalMatrix));
    }

    // draw teapot


    house.Draw(shader);

    

    



    

    
}



void renderScene() {
    depthMapShader.useShaderProgram();
    

    glUniformMatrix4fv(glGetUniformLocation(depthMapShader.shaderProgram, "lightSpaceTrMatrix"), 1, GL_FALSE, glm::value_ptr(computeLightSpaceTrMatrix()));
    glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
    glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
    glClear(GL_DEPTH_BUFFER_BIT);
    //render scene = draw objects
  
    //glCheckError();
    drawObjects(depthMapShader, 1);
    //glCheckError();
    glBindFramebuffer(GL_FRAMEBUFFER, 0); 

    // final scene rendering pass (with shadows)
    
    glViewport(0, 0, myWindow.getWindowDimensions().width, myWindow.getWindowDimensions().height);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    myBasicShader.useShaderProgram();
    
    lightRotation = glm::rotate(glm::mat4(1.0f), glm::radians(angleOfLight), glm::vec3(0.0f, 1.0f, 0.0f));
    glUniform3fv(lightDirLoc, 1, glm::value_ptr(glm::inverseTranspose(glm::mat3(view * lightRotation))*lightDir));
    

    //bind the shadow map
    
    
    glActiveTexture(GL_TEXTURE3);
    
    glBindTexture(GL_TEXTURE_2D, depthMapTexture);
    
    glUniform1i(glGetUniformLocation(myBasicShader.shaderProgram, "shadowMap"), 3);
    glUniformMatrix4fv(glGetUniformLocation(myBasicShader.shaderProgram, "lightSpaceTrMatrix"),
        1,
        GL_FALSE,
        glm::value_ptr(computeLightSpaceTrMatrix()));
    

    drawObjects(myBasicShader, false);



    //skyboxShader.useShaderProgram();
    

    mySkyBox.Draw(skyboxShader, view, projection);

}

void cleanup() {
    myWindow.Delete();
    //cleanup code for your own data
}

int main(int argc, const char* argv[]) {

    try {
        initOpenGLWindow();
    }
    catch (const std::exception& e) {
        std::cerr << e.what() << std::endl;
        return EXIT_FAILURE;
    }

    glGenTextures(1, &textureID);
    glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
    initCubeBox();
    initOpenGLState();
    initModels();
    std::cout << "mod";
    initShaders();
    std::cout << "sha";
    initUniforms();
    std::cout << "uni";
    setWindowCallbacks();
    std::cout << "cll";
    initFBO();
    


    glCheckError();
    // application loop
    while (!glfwWindowShouldClose(myWindow.getWindow())) {
        processMovement();
        renderScene();

        glfwPollEvents();
        glfwSwapBuffers(myWindow.getWindow());

        glCheckError();
    }

    cleanup();

    return EXIT_SUCCESS;
}
