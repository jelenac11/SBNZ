package better.me.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.drools.core.ClassObjectFilter;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import better.me.dto.AgeBoundariesDTO;
import better.me.enums.AgeCategory;
import better.me.exceptions.RequestException;
import better.me.model.AgeBoundaries;
import better.me.model.RegisteredUser;
import better.me.modelDB.RegisteredUserDB;
import better.me.repositories.IRegisteredUser;
import better.me.util.RuleBasedSystemUtil;

@Service
public class AgeService {
	
	@Value("${rules.template.age}")
    private String ageTemplate;
	
	@Value("${rules.drl.age}")
    private String ageDRL;
	
	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private KieSession kieSession;
	
	public String changeBoundaries(@Valid AgeBoundariesDTO dto) throws IOException, MavenInvocationException, RequestException {
		if (dto.getTeenFrom() > dto.getYoungAdultFrom() || dto.getYoungAdultFrom() > dto.getAdultFrom() || dto.getAdultFrom() > dto.getElderFrom()) {
			throw new RequestException("Invalid params!");
		}
		
		List<AgeBoundaries> data = new ArrayList<>();
        data.add(new AgeBoundaries(0, dto.getTeenFrom(), "CHILD"));
        data.add(new AgeBoundaries(dto.getTeenFrom(), dto.getYoungAdultFrom(), "TEEN"));
        data.add(new AgeBoundaries(dto.getYoungAdultFrom(),  dto.getAdultFrom(), "YOUNG_ADULT"));
        data.add(new AgeBoundaries(dto.getAdultFrom(),  dto.getElderFrom(), "ADULT"));
        data.add(new AgeBoundaries(dto.getElderFrom(),  Integer.MAX_VALUE, "ELDER"));
        
        PrintWriter writer = new PrintWriter(ageDRL);
        writer.print("");
        writer.close();

        InputStream template = new FileInputStream(ageTemplate);
        String drl = (new ObjectDataCompiler()).compile(data, template);
        Files.write(Paths.get(ageDRL), drl.getBytes(), StandardOpenOption.WRITE);
        RuleBasedSystemUtil.mavenCleanAndInstall();
        
        this.updateUsers();
        return "Rule created!";
	}

	private void updateUsers() {
		List<RegisteredUserDB> allUsers = registeredUserRepository.findAll();
		kieSession.getAgenda().getAgendaGroup("age").setFocus();
		
		allUsers.stream().map(user -> this.kieSession.insert(new RegisteredUser(user)));
		kieSession.fireAllRules();
		
		@SuppressWarnings("unchecked")
		Collection<RegisteredUser> users = (Collection<RegisteredUser>) kieSession
				.getObjects(new ClassObjectFilter(RegisteredUser.class));
		
		for (RegisteredUser u: users) {
			Optional<RegisteredUserDB> db = registeredUserRepository.findById(u.getId());
			db.get().setAgeCategory(AgeCategory.valueOf(u.getAgeCategory()));
			registeredUserRepository.save(db.get());
		}
		
		kieSession.dispose();
	}
	
}
