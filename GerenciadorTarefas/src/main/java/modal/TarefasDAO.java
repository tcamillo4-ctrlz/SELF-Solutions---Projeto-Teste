package modal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefasDAO {

	ConexaoBanco conex = new ConexaoBanco();
	PreparedStatement stm;
	
	//CADASTRAR ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void cadastrarTarefa(TarefasDTO objtarefasDTO){
		String sql = "INSERT INTO Tarefas (nome, tarefa, status) VALUES (?,?,?)";
		
		try(Connection conex = new ConexaoBanco().conectaBD(); PreparedStatement stm = conex.prepareStatement(sql);){

			stm.setString(1, objtarefasDTO.getNome());
			stm.setString(2, objtarefasDTO.getTarefa());
			stm.setString(3, objtarefasDTO.getStatus());
			
			stm.execute();	
			stm.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
	}
	
	
	//CONSULTAR ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<TarefasDTO> consultarTarefa(String nome) {
		
		List<TarefasDTO> tarefas = new ArrayList<>();
		
		String sql = "SELECT * FROM Tarefas WHERE Tarefas.nome = '" + nome + "'";

		try(Connection conex = new ConexaoBanco().conectaBD(); PreparedStatement stm = conex.prepareStatement(sql);){
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()){
				TarefasDTO objTarefaDTO = new TarefasDTO();
				
				objTarefaDTO.setIdTarefa(resultSet.getInt("idTarefa"));
				objTarefaDTO.setNome(resultSet.getString("nome"));
				objTarefaDTO.setTarefa(resultSet.getString("tarefa"));
				objTarefaDTO.setStatus(resultSet.getString("status"));
				tarefas.add(objTarefaDTO);
			}

			resultSet.close();
			stm.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return tarefas;
	}
	
	
	//DELETAR ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deletarTarefa(Integer idTarefa) {
		String sql = "DELETE FROM Tarefas WHERE idTarefa = ?";
		try(Connection conex = new ConexaoBanco().conectaBD(); PreparedStatement stm = conex.prepareStatement(sql);){
			
			stm.setInt(1, idTarefa);
			
			stm.execute();
			stm.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	//ATUALIZAR ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void atualizarTarefa(TarefasDTO objTarefaDTO) {
		String sql = "UPDATE Tarefas SET nome = ?, tarefa = ?, status = ? WHERE idTarefa = ?";
		try(Connection conex = new ConexaoBanco().conectaBD(); PreparedStatement stm = conex.prepareStatement(sql);){
			
			stm.setString(1, objTarefaDTO.getNome());
			stm.setString(2, objTarefaDTO.getTarefa());
			stm.setString(3, objTarefaDTO.getStatus());
			stm.setInt(4, objTarefaDTO.getIdTarefa());

			stm.execute();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}