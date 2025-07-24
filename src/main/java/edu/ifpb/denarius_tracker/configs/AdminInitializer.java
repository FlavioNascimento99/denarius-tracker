package edu.ifpb.denarius_tracker.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import edu.ifpb.denarius_tracker.entities.Correntista;
import edu.ifpb.denarius_tracker.repository.CorrentistaRepository;


@Configuration
public class AdminInitializer implements ApplicationRunner {

	@Autowired
	private CorrentistaRepository correntistaRepository;
	
	@Override
	public void run(ApplicationArguments args) {
		if (correntistaRepository.findByEmail("admin@denarius.com") == null) {
			
			Correntista admin = new Correntista();
			admin.setNome("Administrador");
			admin.setEmail("admin@denarius.com");
			admin.setSenha("admin");
			admin.setAdmin(true);
			
			correntistaRepository.save(admin);

			System.out.print("Administrador criado.");
		} else {
			System.out.print("Administrador já existente");
		}
	}
	
	
	
	
}
