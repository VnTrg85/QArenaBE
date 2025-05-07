package qarenabe.qarenabe.repository.RepoCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.dto.MemberRequestDTO;
import qarenabe.qarenabe.entity.User;

import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MemberRepoCustom {

    @PersistenceContext
    EntityManager entityManager;

    private void joinTable(StringBuilder sql) {
        sql.append(" FROM user u ");
        sql.append(" INNER JOIN user_role ur ON u.role_id = ur.id ");
    }

    private void querySpecial(StringBuilder where, MemberRequestDTO memberRequestDTO) {
        if (memberRequestDTO.getRoleId() != null) {
            where.append(" AND u.role_id = ").append(memberRequestDTO.getRoleId());
        }
        if (memberRequestDTO.getEmail() != null) {
            where.append(" AND u.email = '").append(memberRequestDTO.getEmail()).append("'");
        }
    }

    public List<User> SearchMemberCustom(MemberRequestDTO memberRequestDTO) {
        StringBuilder sql = new StringBuilder("SELECT u.* ");
        joinTable(sql);

        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        querySpecial(where, memberRequestDTO);

        sql.append(where);

        int offset = memberRequestDTO.getOffset();
        int limit = memberRequestDTO.getLimit();
        sql.append(" LIMIT ").append(limit)
                .append(" OFFSET ").append(offset * limit);
        Query query = entityManager.createNativeQuery(sql.toString(), User.class);
        return query.getResultList();
    }
}
