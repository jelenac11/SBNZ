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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import better.me.dto.CategoryBoundariesDTO;
import better.me.enums.Category;
import better.me.events.ScoreChangedEvent;
import better.me.exceptions.RequestException;
import better.me.model.CategoryBoundaries;
import better.me.model.RegisteredUser;
import better.me.modelDB.RegisteredUserDB;
import better.me.repositories.IRegisteredUser;
import better.me.util.RuleBasedSystemUtil;

@Service
public class CategoryService {
	
	@Value("${rules.template.category}")
    private String categoryTemplate;
	
	@Value("${rules.drl.category}")
    private String categoryDRL;
	
	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	@Qualifier(value = "cepSession")
	private KieSession cep;
	
	public String changeBoundaries(@Valid CategoryBoundariesDTO dto) throws IOException, MavenInvocationException, RequestException {
		if (dto.getIntermediateFrom() > dto.getAdvancedFrom() || dto.getIntermediateFrom() > dto.getProFrom() || dto.getAdvancedFrom() > dto.getProFrom()) {
			throw new RequestException("Invalid params!");
		}
		
		List<CategoryBoundaries> data = new ArrayList<>();
        data.add(new CategoryBoundaries("BEGINNER", 0, dto.getIntermediateFrom()));
        data.add(new CategoryBoundaries("INTERMEDIATE", dto.getIntermediateFrom(), dto.getAdvancedFrom()));
        data.add(new CategoryBoundaries("ADVANCED", dto.getAdvancedFrom(), dto.getProFrom()));
        data.add(new CategoryBoundaries("PRO", dto.getProFrom(), Integer.MAX_VALUE));
        
        PrintWriter writer = new PrintWriter(categoryDRL);
        writer.print("");
        writer.close();
        
        InputStream template = new FileInputStream(categoryTemplate);
        String drl = (new ObjectDataCompiler()).compile(data, template);
        Files.write(Paths.get(categoryDRL), drl.getBytes(), StandardOpenOption.WRITE);
        RuleBasedSystemUtil.mavenCleanAndInstall();
        
        List<RegisteredUserDB> allUsers = registeredUserRepository.findAll();

		for (RegisteredUserDB u: allUsers) {
			RegisteredUser ru = new RegisteredUser(u);
			cep.insert(ru);
			cep.insert(new ScoreChangedEvent(ru));
		}
		cep.fireAllRules();
		
		@SuppressWarnings("unchecked")
		Collection<RegisteredUser> results = (Collection<RegisteredUser>) cep
				.getObjects(new ClassObjectFilter(RegisteredUser.class));
		for ( RegisteredUser row : results ) {
			Optional<RegisteredUserDB> db = registeredUserRepository.findById(row.getId());
			db.get().setCategory(Category.valueOf(row.getCategory()));
			registeredUserRepository.save(db.get());
		}

        return "Rule created!";
	}
	
}
