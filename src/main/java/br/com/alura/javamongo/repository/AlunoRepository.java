package br.com.alura.javamongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import br.com.alura.javamongo.codec.AlunoCodec;
import br.com.alura.javamongo.model.Aluno;

@Repository
public class AlunoRepository {

	private MongoClient cliente;
	private MongoDatabase bancoDeDados;
	
	private void criaConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		
		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(alunoCodec));
		
		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();
		
		
		cliente = new MongoClient("localhost:27017", opcoes);
		bancoDeDados = cliente.getDatabase("test");
	}

	public void salvar(Aluno aluno) {
		criaConexao();
		MongoCollection<Aluno> alunos = bancoDeDados.getCollection("alunos", Aluno.class);
		alunos.insertOne(aluno);
		cliente.close();
	}
	
	public List<Aluno> obterTodosAlunos(){
		criaConexao();
		MongoCollection<Aluno> alunos = bancoDeDados.getCollection("alunos", Aluno.class);
		
		MongoCursor<Aluno> resultado = alunos.find().iterator();
		
		List<Aluno> alunosEncontrados = new ArrayList<>();
		
		while(resultado.hasNext()) {
			alunosEncontrados.add(resultado.next());
		}
		
		cliente.close();
		return alunosEncontrados;
	}

}
