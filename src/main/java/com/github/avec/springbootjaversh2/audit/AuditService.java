package com.github.avec.springbootjaversh2.audit;

import com.github.avec.springbootjaversh2.person.Person;
import com.github.avec.springbootjaversh2.person.PersonRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuditService {
    private final PersonRepository repository;
    private final Javers javers;

    public void displayDifferences(@NonNull String firstname) {

        Optional<Person> maybePerson = repository.findByFirstnameIgnoreCase(firstname);
        maybePerson.ifPresent(person -> {
            JqlQuery query = QueryBuilder.byInstance(person).build();
            List<Shadow<Person>> shadows = javers.findShadows(query);

            if(shadows.size() > 2) {
                Shadow<Person> last = shadows.get(0);
                Shadow<Person> first = shadows.get(2);

                Diff diff = javers.compare(first.get(), last.get());
                log.debug("v{} vs v{} -> \n {}", first.getCommitId().value(), last.getCommitId().value(), diff.prettyPrint());
            }

            shadows.forEach(shadow -> log.debug("{}", shadow));

            List<CdoSnapshot> snapshots = javers.findSnapshots(query);
            snapshots.forEach(cdoSnapshot -> log.debug("{}", cdoSnapshot));

            Changes changes = javers.findChanges(query);
            log.debug("\nPretty {}", changes.prettyPrint());
            log.debug("\nDev {}", changes.devPrint());

        });

    }
}
