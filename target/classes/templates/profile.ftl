<#import "parts/common.ftl" as c>


<@c.page>

    ${message?ifExists}  <! активация >
<h5>${username}   </h5>
    <form method="post">

        <div class="form-group row"><label class="col-sm-2 col-form-label" > Password:  </label>
            <div class="col-sm-4">
                <input type="password" name="password" class="form-control" placeholder="Password"/> </label>
            </div>
        </div>



            <div class="form-group row"><label class="col-sm-2 col-form-label" > Email:  </label>
                <div class="col-sm-4">
                    <input type="email" name="email" class="form-control" placeholder="email" value="${email!''}" />
                </div>                                       <! если емайл не указан, выдает пуст строку >

            </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <label class="col-sm-2 col-form-label">
        </label> <button  class="btn mybutton" type="Save"> Save  </button>

    </form>

</@c.page>