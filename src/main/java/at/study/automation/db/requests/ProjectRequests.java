package at.study.automation.db.requests;

import at.study.automation.db.connection.PostgresConnection;
import at.study.automation.model.project.Project;

public class ProjectRequests extends BaseRequests implements Create<Project>, Update<Project>, Delete<Project> {

    /**
     * Метод создает запись проекта в таблице public.projects в бд.
     *
     * @param project - проект, который нужно добавить в бд. На его основе будет формироваться запрос.
     */
    @Override
    public void create(Project project) {
        String query = "INSERT INTO public.projects\n" +
                "(id, \"name\", description, homepage, is_public, parent_id, " +
                "created_on, updated_on, identifier, status, lft, rgt, " +
                "inherit_members, default_version_id, default_assigned_to_id)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";

        Integer id = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus().statusCode,
                project.getLft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId()
        ).get(0).get("id");
        project.setId(id);
    }

    /**
     * Метод удаляет проект в таблице public.projects в бд, по переданному в параметрах id.
     *
     * @param id - id проекта, который нужно удалить из базы данных.
     */
    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.projects\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    /**
     * Метод обновляет запись в таблице public.projects в бд у проекта по переданному в параметрах id,
     * на основе данных из переданного в параметрах нового проекта.
     *
     * @param id      - Id проекта, который нужно обновить в базе данных.
     * @param project - проект, чьи данные нужно использовать для обновления.
     */
    @Override
    public void update(Integer id, Project project) {
        String query = "UPDATE public.projects\n" +
                "SET \"name\"=?, description=?, " +
                "homepage=?, is_public=?, parent_id=?, " +
                "created_on=?, updated_on=?, identifier=?, status=?, lft=?, rgt=?, " +
                "inherit_members=?, default_version_id=?, default_assigned_to_id=?\n" +
                "WHERE id=?;\n";

        PostgresConnection.INSTANCE.executeQuery(
                query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIsPublic(),
                project.getParentId(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus().statusCode,
                project.getLft(),
                project.getRgt(),
                project.getInheritMembers(),
                project.getDefaultVersionId(),
                project.getDefaultAssignedToId(),
                id
        );
    }
}
