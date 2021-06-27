/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Sconfitti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Double x = null;
    	try {
    		x = Double.parseDouble(txtGoals.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero minimo di goal nel formato corretto");
    		return;
    	}
    	this.model.creaGrafo(x);
    	txtResult.appendText("Grafo creato\n");
    	txtResult.appendText("# VERTICI: " + this.model.getNumeroVertici()+ "\n");
    	txtResult.appendText("# ARCHI: " + this.model.getNumeroArchi());

    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	txtResult.clear();
    	String s=txtK.getText();
    	model.run(Integer.parseInt(s));
    	String ss="GRADO: "+model.getGrado()+"\n";
    	for (Player p:model.getDreamTeam()) {
    		ss+=p.toString()+"\n";
    	}
        txtResult.setText(ss);
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	txtResult.clear();
    	List<Sconfitti> sconfitti=this.model.topPlayer();
    	Player topPlayer = this.model.getTop();
    	if(topPlayer == null) {
    		txtResult.appendText("Crea il grafo!");
    		return;
    	}
    	String s="";
    	for (Sconfitti ss:sconfitti) {
    		s+=ss.getP().toString()+"\n";
    	}
    	txtResult.appendText("TOP PLAYER: " + topPlayer.toString());
    	txtResult.appendText("\n\nAVVERSARI BATTUTI:\n"+s);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
