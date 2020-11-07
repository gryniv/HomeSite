<#import "parts/common.ftlh" as c>

<@c.page>
    admin
    <table  class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Username</th>
            <th scope="col">Status</th>
            <th scope="col">Role</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <th scope="row">${user_index + 1}</th>
                <td>${user.username}</td>
                <td><span class="badge badge-${user.isActive()?then("success", "secondary")}">${user.isActive()?then("Active", "Inactive")}</span></td>
                <td><#list user.roles as role>${role?lower_case}<#sep>, </#list></td>
                <td><a href="/adminpanel/useredit/${user.id}"><i class="fas fa-user-edit"></i></a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>
