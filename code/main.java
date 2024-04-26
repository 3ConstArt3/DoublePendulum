float g = 0.3f; // gravitational constant

float r1;  // length of the 1st rope
float a1V; // angular velocity of the 1st pendulum
float a1;  // angle of the 1st pendulum
float m1;  // mass of the 1st pendulum

float r2;  // length of the 2nd rope
float a2V; // angular velocity of the 2nd pendulum
float a2;  // angle of the 2nd pendulum
float m2;  // mass of the 2nd pendulum

PGraphics pathTracer; // Holds the pathTracer of the 2nd pendulum

void setup()
{
  surface.setTitle("Double Pendulum");
  surface.setResizable(false);
  surface.setLocation(displayWidth / 3, floor(0.1 * displayHeight));

  r1 = 0.3 * height;
  a1V = 0f;
  a1 = PI / 3;
  m1 = 0.3 * r1;

  r2 = 0.12 * height;
  a2V = 0f;
  a2 = PI / 6;
  m2 = 0.15 * r2;

  pathTracer = createGraphics(width, height);

  size(810, 600);
}

void draw()
{
  background(90, 60, 72);
  
  image(pathTracer, 0, 0);
  translate(width / 2, 0.3 * height);

  // Evaluate obj1 angular acceleration
  var obj1num1 = -g * (2 * m1 + m2) * sin(a1);
  var obj1num2 = -g * m2 * sin(a1 - 2 * a2);
  var obj1num3 = -2 * sin(a1 - a2) * m2 * (a2V * a2V * r2 + a1V * a1V * r1 * cos(a1 - a2));
  var obj1den = r1 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2));
  var a1A = (obj1num1 + obj1num2 + obj1num3) / obj1den;

  // Evaluate obj2 angular acceleration
  var obj2num1 = 2 * sin(a1 - a2);
  var obj2num2 = a1V * a1V * r1 * (m1 + m2);
  var obj2num3 = g * (m1 + m2) * cos(a1);
  var obj2num4 = a2V * a2V * r2 * m2 * cos(a1 - a2);
  var obj2den = r2 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2));
  var a2A = obj2num1 * (obj2num2 + obj2num3 + obj2num4) / obj2den;

  // Draw object1
  var obj1X = r1 * sin(a1);
  var obj1Y = r1 * cos(a1);

  stroke(100, 120, 120);
  strokeWeight(3);

  line(0, 0, obj1X, obj1Y);

  fill(90, 90, 120);
  circle(obj1X, obj1Y, m1);

  // Draw object2
  var obj2X = obj1X + r2 * sin(a2);
  var obj2Y = obj1Y + r2 * cos(a2);

  line(obj1X, obj1Y, obj2X, obj2Y);
  circle(obj2X, obj2Y, m2);

  a1V += a1A;
  a1 += a1V;

  a2V += a2A;
  a2 += a2V;

  // Draw the pathTracer tracer
  pathTracer.beginDraw();
  pathTracer.translate(width / 2, 0.3 * height);

  pathTracer.stroke(100, 120, 150);
  pathTracer.strokeWeight(4);
  pathTracer.point(obj2X, obj2Y);

  pathTracer.endDraw();
}
