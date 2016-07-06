CREATE TYPE cypher_type AS ENUM (
    'Anoetic',
    'Occultic'
	);

CREATE TYPE form_type AS ENUM (
    'Usable',
    'Internal',
    'Wearable'
	);

CREATE FUNCTION item_type(n_id integer) RETURNS character varying
    LANGUAGE sql
    AS $_$
	SELECT CASE WHEN ($1 IN (SELECT n_id FROM numenera_item))
	       THEN
			CASE WHEN ($1 IN (SELECT cyp_id FROM cypher))
			THEN
				'Cypher'
			ELSE
				CASE WHEN ($1 IN (SELECT art_id FROM artifact))
				THEN
					'Artifact'
				ELSE
					'N/A'
				END
			END
		ELSE
			'N/A' 
	END
$_$;

CREATE TABLE artifact (
    art_id integer NOT NULL,
    form text NOT NULL,
    dep_roll smallint,
    dep_threshold smallint,
    notes text,
    CONSTRAINT artifact_dep_roll_check CHECK (((dep_roll IS NULL) OR 
											   (dep_roll >= 1))),
    CONSTRAINT artifact_dep_threshold_check CHECK (((dep_threshold IS NULL) OR 
												    (dep_threshold >= 1))),
    CONSTRAINT dep_valid CHECK ((((dep_threshold IS NULL) AND 
								  (dep_roll IS NULL)) OR 
								 (((dep_threshold IS NOT NULL) AND 
								   (dep_roll IS NOT NULL)) AND 
								   (dep_threshold <= dep_roll))))
	);


CREATE TABLE cypher (
    cyp_id integer NOT NULL,
    type cypher_type NOT NULL
	);


CREATE TABLE cypher_forms (
    cyp_id integer NOT NULL,
    form_id integer NOT NULL
	);


CREATE TABLE effects_table (
    table_id integer NOT NULL,
    low_roll smallint,
    high_roll smallint,
    effect text NOT NULL,
    CONSTRAINT effects_table_check CHECK ((low_roll <= high_roll)),
    CONSTRAINT effects_table_high_roll_check CHECK ((high_roll >= 1)),
    CONSTRAINT effects_table_low_roll_check CHECK ((low_roll >= 1))
	);


CREATE TABLE forms (
    form_id integer NOT NULL,
    form text NOT NULL,
    type form_type NOT NULL
	);


CREATE SEQUENCE forms_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE numenera_item (
    n_id integer NOT NULL,
    name text NOT NULL,
    min_level smallint NOT NULL,
    max_level smallint NOT NULL,
    effect text NOT NULL,
    table_id integer,
    CONSTRAINT level_range_check CHECK ((min_level <= max_level)),
    CONSTRAINT numenera_item_max_level_check CHECK ((max_level >= 1)),
    CONSTRAINT numenera_item_min_level_check CHECK ((min_level >= 1))
	);


CREATE SEQUENCE numenera_item_n_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE tables (
    id integer NOT NULL,
    name text NOT NULL,
    roll text NOT NULL
	);


CREATE SEQUENCE tables_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ONLY forms ALTER COLUMN form_id SET DEFAULT nextval('forms_form_id_seq'::regclass);


ALTER TABLE ONLY numenera_item ALTER COLUMN n_id SET DEFAULT nextval('numenera_item_n_id_seq'::regclass);


ALTER TABLE ONLY tables ALTER COLUMN id SET DEFAULT nextval('tables_id_seq'::regclass);


ALTER TABLE ONLY artifact
    ADD CONSTRAINT artifact_pkey PRIMARY KEY (art_id);


ALTER TABLE ONLY cypher_forms
    ADD CONSTRAINT cypher_forms_pkey PRIMARY KEY (cyp_id, form_id);


ALTER TABLE ONLY cypher
    ADD CONSTRAINT cypher_pkey PRIMARY KEY (cyp_id);


ALTER TABLE ONLY effects_table
    ADD CONSTRAINT effects_table_pkey PRIMARY KEY (table_id, effect);


ALTER TABLE ONLY forms
    ADD CONSTRAINT forms_form_type_key UNIQUE (form, type);


ALTER TABLE ONLY forms
    ADD CONSTRAINT forms_pkey PRIMARY KEY (form_id);


ALTER TABLE ONLY numenera_item
    ADD CONSTRAINT numenera_item_pkey PRIMARY KEY (n_id);


ALTER TABLE ONLY tables
    ADD CONSTRAINT tables_name_key UNIQUE (name);


ALTER TABLE ONLY tables
    ADD CONSTRAINT tables_pkey PRIMARY KEY (id);


ALTER TABLE ONLY numenera_item
    ADD CONSTRAINT unique_name UNIQUE (name);


ALTER TABLE ONLY artifact
    ADD CONSTRAINT artifact_art_id_fkey FOREIGN KEY (art_id) REFERENCES numenera_item(n_id) ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE ONLY cypher
    ADD CONSTRAINT cypher_cyp_id_fkey FOREIGN KEY (cyp_id) REFERENCES numenera_item(n_id) ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE ONLY cypher_forms
    ADD CONSTRAINT cypher_forms_cyp_id_fkey FOREIGN KEY (cyp_id) REFERENCES cypher(cyp_id) ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE ONLY effects_table
    ADD CONSTRAINT effects_table_table_id_fkey FOREIGN KEY (table_id) REFERENCES tables(id) ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE ONLY numenera_item
    ADD CONSTRAINT numenera_item_table_id_fkey FOREIGN KEY (table_id) REFERENCES tables(id) ON UPDATE CASCADE ON DELETE CASCADE;
