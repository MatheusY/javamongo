package br.com.alura.javamongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.javamongo.model.Aluno;
import br.com.alura.javamongo.repository.AlunoRepository;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/aluno/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("aluno", new Aluno());
		return "aluno/cadastrar";
	}
	
	@PostMapping("/aluno/salvar")
	public String salvar(@ModelAttribute Aluno aluno) {
		alunoRepository.salvar(aluno);
		return "redirect:/";
	}
	
	@GetMapping("/aluno/listar")
	public String listar(Model model) {
		List<Aluno> alunos = alunoRepository.obterTodosAlunos();
		model.addAttribute("alunos", alunos);
		return "aluno/listar";
	}
}
