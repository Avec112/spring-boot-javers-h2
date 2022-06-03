package com.github.avec.springbootjaversh2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.github.avec.springbootjaversh2.person.Person;
import com.github.avec.springbootjaversh2.person.PersonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public CommandLineRunner createData(final PersonRepository repository) {
		return args -> {
			List<Person> persons = List.of(
					new Person(null, "Donald", "Duck", LocalDate.of(1931, 1, 1)),
					new Person(null, "Dolly", "Duck", LocalDate.of(1932, 2, 2))
			);

			repository.saveAll(persons);

			// print all
			log.debug("Before change");
			repository.findAll().forEach(person -> log.debug("{}", person));


			// Change something
			Optional<Person> maybeDonald = repository.findByFirstnameIgnoreCase("Donald");
			maybeDonald.ifPresent(person -> {
				person.setDateOfBirth(LocalDate.of(1930, 12, 31));
				repository.save(person);
			});

			// print all
			log.debug("After change");
			repository.findAll().forEach(person -> log.debug("{}", person));
		};
	}

}
