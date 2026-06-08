public aspect ExampleAspect {

    //Match(method call) a Specific Method Exactly
    pointcut pc_account_deposit_method(): execution(public void Account.deposit(int));

    //Match(method call) Any Method with Wildcards
    pointcut pc_any_method_inside_account(): execution(* Account.*(..));

    //Match(method call) an Entire Package
    pointcut pc_entire_package(): execution(* org.example.*.*(..));

    //Match(method call) a Package and All Sub-packages
    pointcut pc_entire_package_with_subpackages(): execution(* org.example..*.*(..));

    //Match(method call, field access, constructor call) by Runtime Object Type (target)
    pointcut pc_account(): target(Account);

    //Match(method call) any method with first argument as String
    pointcut pc_any_method_where_first_arg_is_String(): execution(* *.*(..)) && args(java.lang.String, ..);

    //Match(method call) any method in package(and subpackages) not originating from the Account class.
    pointcut  pc_all_methods_not_called_from_inside_Account_class():
            execution(* org.example..*.*(..)) && !cflowbelow(execution(* Account.*(..)));
}
