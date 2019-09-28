/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private boolean isPressed = true;
  private boolean wasPressed = false;
  private int durationPressed = 0;
  private DigitalInput digin;
  private AnalogInput anain;

  private WPI_TalonSRX motor1;
  private int motor1Pos;
  private double leftStickY;
  private Joystick joy;

  private boolean isControlling;



  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    digin = new DigitalInput(4);
    anain = new AnalogInput(1);

    motor1 = new WPI_TalonSRX(2);
    motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    motor1.overrideLimitSwitchesEnable(false);
    motor1.setInverted(!false);
    motor1.configAllowableClosedloopError(0, 1);
    motor1.selectProfileSlot(0, 0);
    motor1.config_kP(0, 1);
    motor1.config_kI(0, 0.00003);
    motor1.setSensorPhase(true);
    

    joy = new Joystick(1);



  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {

    motor1.setSelectedSensorPosition(0);
    
    System.out.println("Hi");

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

    /* Digial input */
    isPressed = digin.get();

    if (isPressed) {
      durationPressed++;

      /* Analog input */
      ///System.out.print("Voltage: " + anain.getVoltage());
      ///System.out.println(" | Value: " + anain.getValue());
      
      
    }

    if (!wasPressed && isPressed) {
      System.out.println("Pressed");
    }
    if (wasPressed && !isPressed) {
      System.out.println("Released. Duration = " + durationPressed);
      durationPressed = 0;
    }

    wasPressed = isPressed;


   
if (isControlling) {
    leftStickY = joy.getRawAxis(1);
    if (Math.abs(leftStickY) < 0.025) {// Clip joystick speed
      leftStickY = 0;
    }
    motor1.set(leftStickY);
  }

    
    if (joy.getRawButton(1)) {
      motor1.set(ControlMode.Position, 2000);
      isControlling = false;
    } else if (joy.getRawButton(2)) {
      motor1.set(ControlMode.Position, 0);
      isControlling = false;
    } else if (joy.getRawButton(3)) {
       isControlling = true;
    }
   

   

    motor1Pos = motor1.getSensorCollection().getQuadraturePosition();


    System.out.println(motor1Pos);




  }



}
