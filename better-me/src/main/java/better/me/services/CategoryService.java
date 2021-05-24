package better.me.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.drools.template.ObjectDataCompiler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import better.me.dto.CategoryBoundariesDTO;
import better.me.exceptions.RequestException;
import better.me.model.CategoryBoundaries;
import better.me.util.RuleBasedSystemUtil;

@Service
public class CategoryService {
	
	@Value("${rules.template.category}")
    private String categoryTemplate;
	
	@Value("${rules.drl.category}")
    private String categoryDRL;
	
	public String changeBoundaries(@Valid CategoryBoundariesDTO dto) throws IOException, MavenInvocationException, RequestException {
		if (dto.getIntermediateFrom() > dto.getAdvancedFrom() || dto.getIntermediateFrom() > dto.getProFrom() || dto.getAdvancedFrom() > dto.getProFrom()) {
			throw new RequestException("Invalid params!");
		}
		
		List<CategoryBoundaries> data = new ArrayList<>();
        data.add(new CategoryBoundaries("Beginner Category", "BEGINNER", 0, dto.getIntermediateFrom()));
        data.add(new CategoryBoundaries("Intermediate Category", "INTERMEDIATE", dto.getIntermediateFrom(), dto.getAdvancedFrom()));
        data.add(new CategoryBoundaries("Advanced Category", "ADVANCED", dto.getAdvancedFrom(), dto.getProFrom()));
        data.add(new CategoryBoundaries("Pro Category", "PRO", dto.getProFrom(), Integer.MAX_VALUE));
        
        new FileOutputStream(categoryDRL).close();
        RuleBasedSystemUtil.mavenCleanAndInstall();
        
        InputStream template = new FileInputStream(categoryTemplate);
        String drl = (new ObjectDataCompiler()).compile(data, template);
        Files.write(Paths.get(categoryDRL), drl.getBytes(), StandardOpenOption.APPEND);
        RuleBasedSystemUtil.mavenCleanAndInstall();
        return "Rule created!";
	}
	
}
