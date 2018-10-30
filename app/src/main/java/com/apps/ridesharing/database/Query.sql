

CREATE TABLE ad_posts (id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	owner_name TEXT, 
	owner_email TEXT, 
	owner_mobile TEXT, 
	owner_mobile_hide TEXT, 
	property_type TEXT, 
	renter_type TEXT, 
	rent_price TEXT, 
	bedrooms TEXT, 
	bathrooms TEXT, 
	square_footage TEXT, 
	amenities TEXT, 
	location TEXT, 
	address TEXT, 
	latitude TEXT, 
	longitude TEXT, 
	description TEXT, 
	image_name TEXT, 
	images_path TEXT,
	is_enable TEXT, 
	created_by_id TEXT, 
	updated_by_id TEXT, 
	created_at TEXT);
	
CREATE TABLE users (id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	user_mobile_number TEXT,
	user_full_name TEXT,
	user_email TEXT,
	user_birth_date TEXT,
	user_nid TEXT,
	user_gender TEXT,
	user_image_name TEXT,
	user_image_path TEXT,
	user_latitude TEXT,
	user_longitude TEXT,
	created_by_id TEXT,
	updated_by_id TEXT, 
	created_at TEXT);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	