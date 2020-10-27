package lol;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Sairu - a robot by  Sairu
 * baseado num tracker
 */
import java.awt.*;

public class Sairu extends AdvancedRobot {
       int moveDirection=1;//Direção de movimento
   
    public void run() {
        
       	setBodyColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
        setGunColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
        setRadarColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
        setBulletColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
        setScanColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);//radar irá girar independente da virada o robô
        
        do{
            turnRadarRightRadians(Double.POSITIVE_INFINITY);
        }while(true);
    }

   
    public void onScannedRobot(ScannedRobotEvent e) {
		double bulletPower = 1.0 + Math.random()*2.0;
        double bulletSpeed = 20 - 3 * bulletPower;
        double absBearing=e.getBearingRadians()+getHeadingRadians();//
        double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//Velocidade dos inimigos
        double gunTurnAmt;//Quantidade para virar o robo
		

        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//Define o radar do robô para virar à esquerda em radianos

        if(Math.random()>.9){
            setMaxVelocity((12*Math.random())+12);//Randomiza a velocide
        }
        if (e.getDistance() > 150) {
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//giro
            setTurnGunRightRadians(gunTurnAmt); //Virar nosso arma
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//Esse aqui preve o movimento dos alemão
            setAhead((e.getDistance() - 140)*moveDirection);//Move pra frente
            setFire(8);//atira
        }
        else{
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
            setTurnGunRightRadians(gunTurnAmt);//Girar a arma
            setTurnLeft(-90-e.getBearing()); //Virar perpedincular ao inimigo
            setAhead((e.getDistance() - 140)*moveDirection);//Mover para frente
            setFire(4);//fogo
        }
		double enemyLatVel = e.getVelocity()*Math.sin(e.getHeadingRadians() - absBearing);
        double escapeAngle = Math.asin(8.0 / bulletSpeed);
		
if(getEnergy() > bulletPower) {
            setFire(bulletPower);
        }
    }
    public void onHitWall(HitWallEvent e){
        moveDirection=-moveDirection;//Muda a direção quando bate na parede
    }
	
    
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }
}