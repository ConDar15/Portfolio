CREATE FUNCTION item_type(n_id INT) RETURNS VARCHAR(8) AS $$
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
$$ LANGUAGE SQL;
					
