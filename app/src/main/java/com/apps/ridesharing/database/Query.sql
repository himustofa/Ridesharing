
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

CREATE TABLE active_users (id INTEGER PRIMARY KEY AUTO_INCREMENT,
	user_mobile_number TEXT,
	user_id TEXT,
	user_latitude TEXT,
	user_longitude TEXT,
	created_by_id TEXT,
	updated_by_id TEXT,
	created_at TEXT);

CREATE TABLE riders (id INTEGER PRIMARY KEY AUTO_INCREMENT,
	rider_mobile_number TEXT,
	rider_password TEXT,
	rider_full_name TEXT,
	rider_email TEXT,
	rider_birth_date TEXT,
	rider_nid TEXT,
	rider_gender TEXT,
	rider_district TEXT,
	rider_vehicle TEXT,
	rider_license TEXT,
	rider_vehicle_no TEXT,
	rider_image_name TEXT,
	rider_image_path TEXT,
	rider_latitude TEXT,
	rider_longitude TEXT,
	created_by_id TEXT,
	updated_by_id TEXT,
	created_at TEXT);

CREATE TABLE active_riders (id INTEGER PRIMARY KEY AUTO_INCREMENT,
	rider_mobile_number TEXT,
	rider_id TEXT,
	rider_latitude TEXT,
	rider_longitude TEXT,
	created_by_id TEXT,
	updated_by_id TEXT,
	created_at TEXT);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	