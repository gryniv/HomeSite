<#import "parts/common.ftlh" as c>

<@c.page>
    <h3 class="col-sm-6">User Editor</h3>

    <div class="form-group row">
    <h5><span class="badge badge-${user.isActive()?then("success", "secondary")}">${user.isActive()?then("Active", "Not Active")} </span>
        ${user.username}
    </h5>
    </div>

    <form action="/adminpanel" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">UserName:</label>
            <div class="col-sm-6">
                <input type="text" name="username" value="${user.username}" placeholder="Enter new UserName"
                       class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-6">
                <input type="text" name="email" value="${user.email}" placeholder="some@some.com"
                       class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="text" name="password" placeholder="Enter new password"
                       class="form-control"/>
            </div>
        </div>

        <#list roles as role>
            <div class="custom-control custom-checkbox">
                <input class="custom-control-input" type="checkbox" id="${role}"
                       name="${role}" ${user.roles?seq_contains(role)?string("checked", "")} />
                <label class="custom-control-label" for="${role}">
                    ${role?lower_case}</label>
            </div>
        </#list>


        <div>
            <div class="custom-control custom-switch">
                <input type="checkbox" class="custom-control-input" id="customSwitch1"
                       name="active" ${user.isActive()?then("checked", "")}/>
                <label class="custom-control-label" for="customSwitch1">Active User</label>
            </div>

        </div>


        <input type="hidden" value="${user.id}" name="userId"/>
        <input type="hidden" value="${_csrf.token}" name="_csrf"/>
        <button class="btn btn-primary" type="submit">Save</button>
    </form>
</@c.page>