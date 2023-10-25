package com.allnote.note;

import com.allnote.tag.Tag;
import com.allnote.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryCustomImpl implements NoteRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Note> findAllWithTags(List<String> tags, Pageable pageable) {
        return findAllByUserWithTags(null, tags, pageable);
    }

    @Override
    public Page<Note> findAllByUserWithTags(User user, List<String> tags, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Note> query = cb.createQuery(Note.class);
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Note> note = query.from(Note.class);
        Root<Note> countNote = countQuery.from(Note.class);

//        solve n+1 problem
        note.fetch("user", JoinType.LEFT);
        note.fetch("tags", JoinType.LEFT);

        addRestrictionOnTagsToNoteQuery(cb, query, note, tags);
        addRestrictionOnTagsToNoteQuery(cb, countQuery, countNote, tags);

        if (user != null) {
            query.where(cb.equal(note.get("user"), user));
            countQuery.where(cb.equal(countNote.get("user"), user));
        }

        TypedQuery<Note> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Note> noteList = typedQuery.getResultList();

        countQuery.select(cb.count(countNote));
        long count = entityManager.createQuery(countQuery).getSingleResult();
        Page<Note> result = new PageImpl<>(noteList, pageable, count);
        return result;
    }

    private <T> void addRestrictionOnTagsToNoteQuery(CriteriaBuilder cb, CriteriaQuery<T> query, Root<Note> note, List<String> tags) {
        List<Predicate> predicates = new ArrayList<>();
        for (String tag : tags) {
            Subquery<Long> subQuery = query.subquery(Long.class);
            Root<Note> subQuestNote = subQuery.from(Note.class);
            Join<Tag, Note> subQueryTag = subQuestNote.join("tags");
            subQuery.select(subQuestNote.get("id")).where(cb.equal(subQueryTag.get("name"), tag));
            predicates.add(cb.in(note.get("id")).value(subQuery));
        }
        query.where(cb.or(predicates.toArray(new Predicate[0])));
    }

}
