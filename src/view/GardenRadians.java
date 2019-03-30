package view;

import processing.core.PApplet;

public class GardenRadians extends PApplet
{

	public static void main(String[] args)
	{
		PApplet.main("view.GardenRadians");
	}

	private float	raggio_h		= 200;
	private float	raggio_h_text	= 240;
	private float	raggio_m		= 150;
	private float	raggio_s		= 160;
	private int		punti_ore		= 12;
	private int		punti_min		= 60;
	private float	angolo_h		= TWO_PI / this.punti_ore;
	private float	angolo_min		= TWO_PI / this.punti_min;
	private float	angolo_secondi	= TWO_PI / this.punti_min;
	private float	angolo_minuti;
	private float	angolo_ore;
	private float	DELTA_m;
	private float	DELTA_h;
	private float	DELTA_s;
	private int		startS;

	private int		milliS;

	private boolean	sincro			= false;

	public void disegnaMin()
	{
		for (int i = 0; i < this.punti_min; i++)
		{
			this.noFill();
			this.ellipse(this.raggio_m * cos(this.angolo_min * i), this.raggio_m * sin(this.angolo_min * i), 10, 10);
		}
	}

	public void disegnaOre()
	{
		for (int i = 1; i < (this.punti_ore + 1); i++)
		{
			this.stroke(255);
			this.noFill();
			this.ellipse(0, 0, this.raggio_h - (i * 20), this.raggio_h - (i * 20)); // Cerchi concetrici
			this.fill(0);
			this.ellipse(this.raggio_h * cos(this.angolo_h * i), this.raggio_h * sin(this.angolo_h * i), 20, 20); // Cerchi ore
			this.fill(255);
			this.textSize(16);
			this.textAlign(CENTER);
			this.text(i, this.raggio_h_text * cos((this.angolo_h * i) - HALF_PI),
			this.raggio_h_text * sin((this.angolo_h * i) - HALF_PI));
		}
	}

	public void disegnaQuadrante()
	{
		this.noFill();
		this.stroke(255);
		this.ellipse(0, 0, this.raggio_h * 2, this.raggio_h * 2);
		this.disegnaOre();
		this.disegnaMin();
	}

	@Override
	public void draw()
	{
		this.startS = second();
		while (((this.startS + 1) != second()) && (this.sincro == false))
		{
			this.milliS = this.millis();
		}
		this.sincro = true;
		this.background(0);
		this.DELTA_s = norm((this.millis() - this.milliS) % 1000, 0, 1000);
		this.angolo_secondi = map(second() + this.DELTA_s, 0, 60, 0, TWO_PI) - HALF_PI;
		this.DELTA_m = norm(second(), 0, 60);
		this.angolo_minuti = map(minute() + this.DELTA_m, 0, 60, 0, TWO_PI) - HALF_PI;
		this.DELTA_h = norm(minute(), 0, 60);
		this.angolo_ore = map(hour() + this.DELTA_h, 0, 12, 0, TWO_PI) - HALF_PI;
		this.translate(this.width / 2, this.height / 2);
		this.noStroke();
		this.orarioDigitale();
		this.disegnaQuadrante();
		this.stroke(255);
		this.line(0, 0, this.raggio_s * cos(this.angolo_secondi), this.raggio_s * sin(this.angolo_secondi));
		this.strokeWeight(2);
		this.line(0, 0, this.raggio_m * cos(this.angolo_minuti), this.raggio_m * sin(this.angolo_minuti));
		this.line(0, 0, this.raggio_h * cos(this.angolo_ore), this.raggio_h * sin(this.angolo_ore));
		this.ellipse(this.raggio_h * cos(this.angolo_ore), this.raggio_h * sin(this.angolo_ore), 20, 20);
		this.strokeWeight(1);
		this.noStroke();
	}

	public void orarioDigitale()
	{
		this.fill(255);
		this.textSize(15);
		this.text("Ora esatta: " + hour() + ":" + minute() + ":" + second(), -200, 280);
	}

	@Override
	public void settings()
	{
		this.size(600, 600);
	}

	@Override
	public void setup()
	{
		this.background(0);
	}

}
