package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;


public class TaskControllerTest {
	Task todo = new Task();
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	TaskController controller;
	
	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao () {
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar a este ponto.");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData () throws ValidationException {
		todo.setTask("Vai dar Certo");
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar a este ponto.");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada () {
		todo.setTask("Vai dar Certo");
		todo.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar a este ponto.");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComSucesso () throws ValidationException {
		todo.setTask("Vai dar Certo");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		Mockito.verify(taskRepo).save(todo);
	}

}
