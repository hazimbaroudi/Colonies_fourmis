package Colonie_Fourmis.Gui;

public class Palette {
	private SettingsPanel settings;
	private Frame mainPanel ; 
		
		
	public Palette () {		
		settings = new SettingsPanel(this);
		mainPanel = new Frame(this);
		mainPanel.setVisible(true);		
	}
		
	public SettingsPanel getSettings() {
		return settings ; 
	}
	
	public void setSettings(SettingsPanel sett) {
		settings = sett  ; 
	}
	
	public Frame getFrame() {
		return mainPanel ; 
	}
	
	// public static void main(String[] args) { 		
	// }
	
	
}
