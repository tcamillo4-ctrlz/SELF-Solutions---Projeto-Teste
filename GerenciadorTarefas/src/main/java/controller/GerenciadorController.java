package controller;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import modal.TarefasDAO;
import modal.TarefasDTO;

public class GerenciadorController {

    @FXML
    private TabPane abas;

    @FXML
    private Tab tabCadastrar;
    
    @FXML
    private Tab tabConsultar;

    @FXML
    private Tab tabEditar;
    
    @FXML
    private TableView<TarefasDTO> tabelaTarefa;
    
    @FXML
    private TableColumn<TarefasDTO, Integer> colIdTarefa;

    @FXML
    private TableColumn<TarefasDTO, String> colNome;

    @FXML
    private TableColumn<TarefasDTO, String> colTarefa;
    
    @FXML
    private TableColumn<TarefasDTO, String> colStatus;

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtTarefa;
    
    @FXML
    private TextField txtTarefaConsultar;

    @FXML
    private TextField txtNomeEditar;

    @FXML
    private TextField txtTarefaEditar;

    @FXML
    private CheckBox ckbCompleted;
    
    @FXML
    private CheckBox ckbCompletedEditar;
    
    
    private TarefasDTO tarefaSelecionada;
    TarefasDAO tarefasDao = new TarefasDAO();
    
    public void initialize() {
    	tarefasDao = new TarefasDAO();
		
    	colIdTarefa.setCellValueFactory(new PropertyValueFactory<>("idTarefa"));
    	colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	colTarefa.setCellValueFactory(new PropertyValueFactory<>("tarefa"));    	
    	colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));  
    }
    
    @FXML
    void btnLimparTarefa() {
    	if (exibiDialogoConfirmacao("Todos os campos serão LIMPOS. Confirmar?")) {
	    	txtNome.clear();
	    	txtTarefa.clear();
	    	ckbCompleted.setSelected(false);
    	}
    }
    
    @FXML
    void btnCadastrarTarefa() {
    	TarefasDTO tarefas = new TarefasDTO();
    	
    	tarefas.setNome(txtNome.getText());
    	tarefas.setTarefa(txtTarefa.getText());
    	if(ckbCompleted.isSelected()) {
    		tarefas.setStatus("Completed");
    	}else{
    		tarefas.setStatus("Not Completed");
    	}
    	
    	try {
			tarefasDao.cadastrarTarefa(tarefas);
			exibiDialogoINFO("Tarefa cadastrada com sucesso!");
			txtNome.clear();
			txtTarefa.clear();
			ckbCompleted.setSelected(false);
		} catch (Exception e) {
			exibiDialogoERRO("Falha ao cadastrar tarefa!");
			e.printStackTrace();
		}
    }

    @FXML
    void btnConsultarNome() {
    	try {
    		List<TarefasDTO> resultado =  tarefasDao.consultarTarefa(txtTarefaConsultar.getText());
        	if(resultado.isEmpty()) {
        		exibiDialogoINFO("Nenhuma tarefa encontrada!");
        	}else {
        		tabelaTarefa.setItems(FXCollections.observableArrayList(resultado));
        	}
		} catch (Exception e) {
			exibiDialogoERRO("Falha ao realizar a consulta de tarefa!");
			e.printStackTrace();
		}
    }

    @FXML
    void btnTarefaAtualizar() {
    	tarefaSelecionada = tabelaTarefa.getSelectionModel().getSelectedItem();
    	if (tabelaTarefa.getSelectionModel().getSelectedItem()== null) {
    		exibiDialogoERRO("Não há tarefa selecionada!");
    	} else {
    		tabEditar.setDisable(false);
    		txtNomeEditar.setText(tarefaSelecionada.getNome());
    		txtTarefaEditar.setText(tarefaSelecionada.getTarefa());
    		
    		ckbCompletedEditar.setSelected(true);
    		if(tarefaSelecionada.getStatus().equals("Completed")) {
        		ckbCompletedEditar.setSelected(true);
        	}else{
        		ckbCompletedEditar.setSelected(false);
        	}
    	}
    }

    @FXML
    void btnTarefaDeletar() {
    	if (tabelaTarefa.getSelectionModel().getSelectedItem()== null) {
    		exibiDialogoERRO("Não há tarefa selecionada!");
    	} else {
    		if (exibiDialogoConfirmacao("Confirmar a exclusão da tarefa selecionada?")) {
    			try{
					tarefasDao.deletarTarefa(tabelaTarefa.getSelectionModel().getSelectedItem().getIdTarefa());;
					exibiDialogoINFO("Tarefa deletada com sucesso!");
					txtTarefaConsultar.clear();
					btnConsultarNome();
    				}catch (Exception e) {
    					exibiDialogoERRO("Falha ao deletar tarefa!");
    				}
    		}
    	}
    }

    @FXML
    void btnTarefaLimparAtt() {
    	if (exibiDialogoConfirmacao("Todos os campos serão LIMPOS. Confirmar?")) {
        	txtNomeEditar.clear();
        	txtTarefaEditar.clear();
        	ckbCompletedEditar.setSelected(false);
    	}
    }

    @FXML
    void btnTarefaSalvarAtt() {
    	tarefaSelecionada = tabelaTarefa.getSelectionModel().getSelectedItem();
    	
    	tarefaSelecionada.setNome(txtNomeEditar.getText());
    	tarefaSelecionada.setTarefa(txtTarefaEditar.getText());
    	if(ckbCompletedEditar.isSelected()) {
    		tarefaSelecionada.setStatus("Completed");
    	}else{
    		tarefaSelecionada.setStatus("Not Completed");
    	}
    	
	    try {
	    	tarefasDao.atualizarTarefa(tarefaSelecionada);
	    	exibiDialogoINFO("Tarefa atualizada com sucesso!");
			abas.getSelectionModel().select(tabConsultar);
			btnConsultarNome();
			tabEditar.setDisable(true);
		} catch (Exception e) { 			
			exibiDialogoERRO("Falha ao atualizar tarefa!");
		}
    }
        

    @FXML
    void gerenciarAbas() {
    	if ((tabConsultar.isSelected()) || (tabCadastrar.isSelected())) {
    		tabEditar.setDisable(true);
    	}
    }

    private void exibiDialogoINFO(String informacao) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Informacao");
    	alert.setHeaderText(null);
    	alert.setContentText(informacao);
    	
    	alert.showAndWait();
    }
    
     void exibiDialogoERRO(String erro) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Erro");
    	alert.setHeaderText(null);
    	alert.setContentText(erro);
    	
    	alert.showAndWait();
     }

     private boolean exibiDialogoConfirmacao(String confirmacao) {
      	Alert alert = new Alert(AlertType.CONFIRMATION);
      	alert.setTitle("Confirmacao");
      	alert.setHeaderText(null);
      	alert.setContentText(confirmacao);
      	
      	Optional<ButtonType> opcao = alert.showAndWait();
      	
      	if(opcao.get() == ButtonType.OK)
      		return true;
      	return false;
      }  
    
}