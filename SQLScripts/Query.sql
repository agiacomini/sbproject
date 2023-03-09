SET GLOBAL log_bin_trust_function_creators = 1;
select @@log_bin_trust_function_creators;


SHOW GRANTS FOR sbuser@localhost;
GRANT ALL PRIVILEGES ON sbdatabase.* TO 'sbuser'@'localhost';
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'sbuser'@'localhost';

DELIMITER |
CREATE TRIGGER authorities_IU_TRG BEFORE INSERT ON authorities
FOR EACH ROW
BEGIN

	DECLARE vUser VARCHAR(30) DEFAULT 0;

    SELECT current_user() INTO vUser;

	SET NEW.created = NOW();
    SET NEW.lastUpdate = SYSDATE();
    SET NEW.createdBy = vUser;
    SET NEW.lastUpdateBy = vUser;

	-- SET NEW.lastUpdate = NOW();
    -- SET NEW.lastUpdateBy = TMPVAR;

END;
|

INSERT INTO sbdatabase.authorities (ID, GRANTS, NAME, CREATED, CREATEDBY, LASTUPDATE, LASTUPDATEBY) VALUES (1, 'ADMIN', 'ADMIN AUTH', NOW(), current_user(), NOW(), current_user());
INSERT INTO sbdatabase.authorities (ID, GRANTS, NAME, CREATED, CREATEDBY, LASTUPDATE, LASTUPDATEBY) VALUES (2, 'USER', 'USER AUTH', NOW(), current_user(), NOW(), current_user());


INSERT INTO sbdatabase.sbgroup(GROUPNAME, GROUPDESCRIPTION, AUTHORITIES, CREATED, CREATEDBY, LASTUPDATE, LASTUPDATEBY) VALUES ('ADMIN GROUP', 'ADMIN GROUP DESCRIPTION', 1, NOW(), current_user(), NOW(), current_user());
INSERT INTO sbdatabase.sbgroup(GROUPNAME, GROUPDESCRIPTION, AUTHORITIES, CREATED, CREATEDBY, LASTUPDATE, LASTUPDATEBY) VALUES ('USER GROUP', 'USER GROUP DESCRIPTION', 2, NOW(), current_user(), NOW(), current_user());