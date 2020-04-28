<#import "parts/common.ftl" as c>

<@c.page>


        <table class="table  table-striped">
            <thead class="thead-dark">
            <tr>
                <th scope="col" style="width: 7%">#</th>
                <th scope="col" style="width: 31%">Name</th>
                <th scope="col" style="width: 31%">Roles</th>
                <th scope="col" style="width: 31%">  </th>

            </tr>
            </thead>
             <#list users as user >
                 <thead>
                 <tr>
                     <th scope="col">${user?counter}</th>
                     <th scope="col">${user.username}</th>
                     <th scope="col"><#list user.roles as role > ${role}<#sep>,   </#list></th>
                     <th scope="col"><a href ="user/${user.id}"> edit  </a></th>

                 </tr>
                 </thead>


                </#list>
        </table>


        </tbody>
    </table>
</@c.page>
