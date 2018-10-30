

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
	
CREATE TABLE feedback (id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	fb_message TEXT, 
	posted_id TEXT, 
	user_id TEXT, 
	user_name TEXT, 
	user_image TEXT, 
	user_image_path TEXT, 
	created_by_id TEXT, 
	updated_by_id TEXT, 
	created_at TEXT);
	
CREATE TABLE notification (id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	user_id TEXT, 
	user_name TEXT, 
	user_marital_status TEXT, 
	user_mobile TEXT, 
	user_address TEXT, 
	user_occupation TEXT, 
	user_image TEXT, 
	user_image_path TEXT, 
	post_id TEXT, 
	post_owner_name TEXT, 
	post_owner_mobile TEXT, 
	post_property_type TEXT, 
	post_image_name TEXT, 
	post_image_path TEXT, 
	created_by_id TEXT, 
	updated_by_id TEXT, 
	created_at TEXT);
	
CREATE TABLE favorite (id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	user_id TEXT, 
	user_mobile TEXT, 
	post_id TEXT, 
	post_owner_mobile TEXT, 
	created_by_id TEXT, 
	updated_by_id TEXT, 
	created_at TEXT);	
	
CREATE TABLE users (id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	user_name TEXT, 
	user_relation TEXT, 
	user_occupation TEXT, 
	user_email TEXT, 
	user_mobile TEXT, 
	user_address TEXT, 
	user_nid TEXT, 
	user_image TEXT, 
	user_path TEXT, 
	is_user_owner TEXT, 
	created_by_id TEXT, 
	updated_by_id TEXT, 
	created_at TEXT);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	