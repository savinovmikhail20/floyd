<#import "parts/common.ftl" as c>

<@c.page>
    User editor

    <form action="/user" method="post">
        <div class="form-group row">
        <div class="col-sm-4">
        <input type="text" name="username" class="form-control" value="${user.username}">
        </div>
        </div>
        <#list roles as role>
            <div class="form-check">
                <label ><input type="checkbox" class="form-check-input" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit"  class="btn mybutton" >Save</button>
    </form>
</@c.page>