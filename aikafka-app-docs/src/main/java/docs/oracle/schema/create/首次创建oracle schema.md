##Oracle新建Schema
1.首先，创建（新）用户
```oracle
 create user USPC_CS identified by USPC_CS;
```

2.创建表空间
```oracle
 create tablespace USPC_CS datafile 'd:\app\cnlm.me\data\data_uspc_cs.dbf' size 512m;
```

3.将空间分配给用户
```oracle
 alter user USPC_CS default tablespace USPC_CS;
```

4.给用户授权
```oracle
 grant create session,create table,unlimited tablespace to USPC_CS;
```
5.连接