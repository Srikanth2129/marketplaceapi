CREATE TABLE  PROJECT(
  PROJECT_ID     INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 10, INCREMENT BY 1),
  TITLE          VARCHAR(50) NOT NULL,
  DESCRIPTION    VARCHAR(100),
  REQUIREMENTS   VARCHAR(100),
  SKILLS		 VARCHAR(100),
  CURRENCY       VARCHAR(50),
  MAXBUDGET      DOUBLE,
  LASTDATE       DATE,
  STATUS         VARCHAR(50) ,
  PROJECT_TYPE   VARCHAR(50) ,
  CREATED_DATE   DATE,

  PRIMARY KEY (PROJECT_ID)
);

CREATE TABLE  BID(
  BID_ID     INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 10, INCREMENT BY 1),
  PROJECT_ID INTEGER NOT NULL,
  BUYER      VARCHAR(50) NOT NULL,
  CURRENCY   VARCHAR(50),
  BID_AMOUNT DOUBLE,
  STATUS     VARCHAR(50),
  CREATED_DATE DATE,

  PRIMARY KEY (BID_ID),
  CONSTRAINT projectId_fk FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(PROJECT_ID)
);