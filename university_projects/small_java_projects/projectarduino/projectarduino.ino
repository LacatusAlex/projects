

#include <LiquidCrystal.h>
#include <TimerOne.h>
#include "pitches.h"

LiquidCrystal lcd(7, 6, 5, 4, 3, 2);

int x=0;
int y=0;
int score=0;
int time_elapsed=0;
bool isDone=false;

//speaker
int puls = 0; // duty cycle, initially 0
int step = 10; // duty cycle increment step
int ledPin = 13; // on-board LED

typedef struct coord{
  int x;
  int y;
}Coord;

Coord coins[5];

const byte STEP1[8] = {
 0b01110,
 0b10001,
 0b10001,
 0b01110,
 0b11111,
 0b00100,
 0b01010,
 0b10001
};
const byte STEP2[8] = {
 0b01110,
 0b10001,
 0b10001,
 0b01110,
 0b11111,
 0b00100,
 0b01010,
 0b01010
};
const byte STEP3[8] = {
 0b01110,
 0b10001,
 0b10001,
 0b01110,
 0b11111,
 0b00100,
 0b00100,
 0b00100
};


const byte COIN1[8] = {
 0b00000,
 0b00100,
 0b01010,
 0b10001,
 0b10001,
 0b01010,
 0b00100,
 0b00000
 
};
const byte COIN2[8] = {
 0b00000,
 0b00100,
 0b01010,
 0b01010,
 0b01010,
 0b01010,
 0b00100,
 0b00000
};
const byte COIN3[8] = {
 0b00000,
 0b00100,
 0b00100,
 0b00100,
 0b00100,
 0b00100,
 0b00100,
 0b00000
};
const byte COIN4[8] = {
 0b00000,
 0b00100,
 0b01010,
 0b01010,
 0b01010,
 0b01010,
 0b00100,
 0b00000
};
const byte COIN5[8] = {
 0b00000,
 0b00100,
 0b01010,
 0b10001,
 0b10001,
 0b01010,
 0b00100,
 0b00000
};





// notes in the melody – constant values defining frequency for each used note
const int MELODY[] = {
  NOTE_C4,
  NOTE_G3,
  NOTE_G3,
  NOTE_A3,
  NOTE_G3,
  0,
  NOTE_B3,
  NOTE_C4,
  NOTE_C4,
  NOTE_G3,
  NOTE_G3,
  NOTE_A3,
  NOTE_G3,
  0,
  NOTE_B3,
  NOTE_C4,
  NOTE_C4,
  NOTE_G3,
  NOTE_G3,
  NOTE_A3,
  NOTE_G3,
  0,
  NOTE_B3,
  NOTE_C4,
  NOTE_C4,
  NOTE_G3,
  NOTE_G3,
  NOTE_A3,
  NOTE_G3,
  0,
  NOTE_B3,
  NOTE_C4,
  NOTE_C4,
  NOTE_G3,
  NOTE_G3,
  NOTE_A3,
  NOTE_G3,
  0,
  NOTE_B3,
  NOTE_C4
};

// note durations: 4 = quarter note, 8 = eighth note, etc.:
const int NOTE_DURATIONS[] = {
  4, // NOTE_C4,
  8, // NOTE_G3,
  8, // NOTE_G3,
  4, // NOTE_A3,
  4, // NOTE_G3,
  4, // 0,
  4, // NOTE_B3,
  4,  // NOTE_C4,
  4, // NOTE_C4,
  8, // NOTE_G3,
  8, // NOTE_G3,
  4, // NOTE_A3,
  4, // NOTE_G3,
  4, // 0,
  4, // NOTE_B3,
  4,  // NOTE_C4,
  4, // NOTE_C4,
  8, // NOTE_G3,
  8, // NOTE_G3,
  4, // NOTE_A3,
  4, // NOTE_G3,
  4, // 0,
  4, // NOTE_B3,
  4,  // NOTE_C4,
  4, // NOTE_C4,
  8, // NOTE_G3,
  8, // NOTE_G3,
  4, // NOTE_A3,
  4, // NOTE_G3,
  4, // 0,
  4, // NOTE_B3,
  4,  // NOTE_C4,
  4, // NOTE_C4,
  8, // NOTE_G3,
  8, // NOTE_G3,
  4, // NOTE_A3,
  4, // NOTE_G3,
  4, // 0,
  4, // NOTE_B3,
  4  // NOTE_C4
};

// Based on Example #4, we take the base value as 1000 milliseconds, in other words one second
// Then we need to consider the values from the noteDurations array ... 1/4 --> 250 ms, 1/8 --> 125 ms
// The pause between the notes also needs to be taken into account, so 250*1.3 = 325 ms, 125*1.3 = 162 ms
// The greatest common factor between these four numbers is GCF([125, 162, 250, 325]) = 1 ms
// So, in other words, we need to cycle the interrupt service routine every 1 ms
const int ISR_PERIOD_TIME = 1000; // 10^3 μseconds --> 1 millisecond (10^-3 seconds)

// Change this to increase / decrease the speed of the walking animation
const unsigned int WALKING_ANIMATION_DELAY = 300;



volatile unsigned int currentNote;
unsigned int playArrayInternalIndex;
unsigned int melodyArrLength;

byte currentImage;

int *playArray;

byte coinframe=3;

void setup(void) {
  melodyArrLength = sizeof(MELODY) / sizeof(int); // Get the length of the melody array
  calculatePlayArray();
  Serial.begin(9600);// Serial 0 interface for PC
  Serial1.begin(9600); 
  pinMode(ledPin, OUTPUT);
  lcd.begin(16,2);
  lcd.createChar(0, STEP1);
  lcd.createChar(1, STEP2);
  lcd.createChar(2, STEP3);
  lcd.createChar(3, COIN1);
  lcd.createChar(4, COIN2);
  lcd.createChar(5, COIN3);
  lcd.createChar(6, COIN4);
  lcd.createChar(7, COIN5);
  coins[0].x=0;
  coins[0].y=1;
  coins[1].x=10;
  coins[1].y=1;
  coins[2].x=3;
  coins[2].y=0;
  coins[3].x=4;
  coins[3].y=1;
  coins[4].x=5;
  coins[4].y=0;
  playAnimation(x,y);
  Timer1.initialize(ISR_PERIOD_TIME); // init the timing interval for event triggering (1s = 10-6s)
  Timer1.attachInterrupt(playMusic); // The function is called at the preset time interval
  
  delay(WALKING_ANIMATION_DELAY);
}

void playMusic() {
  if (currentNote % 2 == 0) {
    const int currentMelodyIndex = currentNote / 2;
    tone(8, MELODY[currentMelodyIndex], playArray[currentNote]);
  } else {
    tone(8, 0, playArray[currentNote]);
  }
  if (playArrayInternalIndex < playArray[currentNote]) {
    playArrayInternalIndex++;
  } else {
    playArrayInternalIndex = 0;
    currentNote++;
    if (currentNote > melodyArrLength * 2) {
      currentNote = 0;
    }
    noTone(8);
  }
}

void calculatePlayArray() {  
  // melodyLength * 2, because you need a pause after every note
  playArray = (int *) malloc(2 * melodyArrLength * sizeof(int));
  
  for (unsigned int i = 0; i < 2 * melodyArrLength; i++) {
    const unsigned int currentMelodyIndex = i / 2;
    const unsigned int durationInMilliSeconds = 1000 / NOTE_DURATIONS[currentMelodyIndex];
    // Ever even note starting with 0 will be a note, odds will be a pause
    if (i % 2 == 0) {
      playArray[i] = durationInMilliSeconds;
    } else {
      playArray[i] = durationInMilliSeconds * 1.3;
    }
  }
}

void playAnimation(int i, int j) {
  lcd.clear();
  lcd.setCursor(i,j);
  lcd.write(currentImage);
  if (currentImage == 2) {
    currentImage = 0;
  } else {
    currentImage++;
  }
}

void moveRight(){
  if(x<15){
    
    x++;
    playAnimation(x,y);
  }

}
void moveLeft(){
  if(x>0){
    
    x--;
    playAnimation(x,y);
  }
}
void moveUp(){
  if(y>0){
    
    y--;
    playAnimation(x,y);
  }
}
void moveDown(){
  if(y<1){
    
    y++;
    playAnimation(x,y);
  }
}



void renderCoins(){
  for(int i=0;i<5;i++){

    if(coins[i].x==x && coins[i].y==y){
      score+=2;
      coins[i].x=15-coins[i].x;
      coins[i].y=1-coins[i].y;
    }
    lcd.setCursor(coins[i].x,coins[i].y);
    
    lcd.write(coinframe);
    
    
  }
  if (coinframe == 6) {
      coinframe=3;
  } else {
      coinframe++;
  }
}

void pulsate_led(){
  analogWrite(ledPin, puls);
  // increment the duty cycle
  puls = puls + step;
  // change the increment direction at the end of the 
  //interval 
  if (puls <= 0 || puls >= 255) {
    step = -step ; 
  }
}

void handle_input(){
  char command=Serial1.read();
  if(!isDone){
    if(command=='w')
      moveUp();
    if(command=='s')
      moveDown();  
    if(command=='a')
      moveLeft();
    if(command=='d')
      moveRight();
  }
  else{
    if(command=='r'){
      x=0;
      y=0;
      lcd.clear();
      time_elapsed=0;
      isDone=false;
      score=0;
      playAnimation(x,y);
    }
    
  }

}

void manageTime(){
  time_elapsed++;
  if(time_elapsed>100){
    isDone=true;
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Game Over");
    lcd.setCursor(0,1);
    lcd.print("Score: ");
    int digit=score/100;
    lcd.print(digit);
    digit=(score/10)%10;
    lcd.print(digit);
    digit=score%10; 
    lcd.print(digit);
  }
  
}



void loop(void) {
  manageTime();

  handle_input();
  if(!isDone){
    renderCoins();
  }
  
  
  delay(WALKING_ANIMATION_DELAY);
}
