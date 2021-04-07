drop table if exists Transactions;
drop table if exists CreditLimits;
drop table if exists Accounts;
drop table if exists OperationTypes;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Accounts (
    Account_Id uuid primary key DEFAULT uuid_generate_v4(),
    Created_At timestamp not null,
    Document_Number varchar(14) not null,
    UNIQUE(Document_Number)
);

CREATE TABLE OperationTypes (
    Operation_Type_Id int not null primary key,
    Description varchar(100) not null
);

CREATE TABLE Transactions (
    Transaction_Id uuid primary key DEFAULT uuid_generate_v4(),
    Event_Date timestamp not null,
    Account_Id uuid not null,
    Amount decimal not null,
    Operation_Type_Id int not null,
    CONSTRAINT fk_accounts_accountid foreign key (Account_Id) references Accounts(Account_Id),
    CONSTRAINT fk_operationtypes_operationtypeid foreign key (Operation_Type_Id) references OperationTypes(Operation_Type_Id)
);

CREATE TABLE CreditLimits (
    Credit_Limit_id uuid primary key DEFAULT uuid_generate_v4(),
    Account_Id uuid not null,
    Created_At timestamp not null,
    Credit_Limit decimal not null,
    CONSTRAINT fk_accounts_accountid foreign key (Account_Id) references Accounts(Account_Id)
)