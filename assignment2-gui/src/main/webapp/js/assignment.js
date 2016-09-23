function getStudentData() {
	// This must be implemented by you. The json variable should be fetched
	// from the server, not initiated with a static value as below.
	// You must first download the student json data from the server
	// then call populateStudentTable(json);
	// and then populateStudentLocationForm(json);
	$.ajax({
		url : 'http://localhost:8080/assignment2-gui/api/student',
		type : 'GET',
		dataType : 'json',
		success : function(response) {
			console.log("RESPONSE TO AJAX:" + response)
			populateStudentTable(response);
			populateStudentLocationForm(response);
		}
	});

}

function populateStudentTable(json) {
	// for each student make a row in the student location table
	// and show the name, all courses and location.
	// if there is no location print "No location" in the <td> instead
	// tip: see populateStudentLocationForm(json) og google how to insert html
	// from js with jquery.
	// Also search how to make rows and columns in a table with html

	// the table can you see in index.jsp with id="studentTable"

	for (var s = 0; s < json.length; s++) {
		var formString = '<tr>';

		var student = json[s];
		student = explodeJSON(student);
		formString += '<td>' + student.name + '</td>';
		formString += '<td>';
		for (var p = 0; p < student.courses.length; p++) {
			var courses = student.courses[p];
			courses = explodeJSON(courses);
			formString += courses.courseCode;
			if (p-1<student.courses.length) 
			{
				formString +=",";
			}
		}
		formString += '</td>';
		if (student.latitude == null && student.longitude == null) {
			formString += '<td>' + "No location" + '</td>';
		} else {
			formString += '<td>' + student.latitude + " " + student.longitude
					+ '</td>';
			var myLatlng = new google.maps.LatLng(student.latitude,
					student.longitude);
			var marker = new google.maps.Marker({
				position : myLatlng,
				map : map,
				title : student.name
			});

		}
		formString += '</tr>';
		$('#studentTable').append(formString);
	}

}

function populateStudentLocationForm(json) {
	var formString = '<tr><td><select id="selectedStudent" name="students">';
	for (var s = 0; s < json.length; s++) {
		var student = json[s];
		student = explodeJSON(student);
		formString += '<option value="' + student.id + '">' + student.name
				+ '</option>';
	}
	formString += '</select></td></tr>';

	$('#studentLocationTable').append(formString);

}

$('#locationbtn').on('click', function(e) {
	e.preventDefault();
	get_location();
});

// This function gets called when you press the Set Location button
function get_location() {
	navigator.geolocation.getCurrentPosition(location_found);
}

// Call this function when you've succesfully obtained the location.
function location_found(position) {
	// Extract latitude and longitude and save on the server using an AJAX call.
	// When you've updated the location, call populateStudentTable(json); again
	// to put the new location next to the student on the page. .
	var selector = document.getElementById('selectedStudent');
	var student = selector[selector.selectedIndex].value;
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	// alert("FUNCTION CALLED!");
	// $.getJSON("http://localhost:8080/assignment2-gui/api/student/" + student
	// + "/location?latitude=" + latitude + "&longitude=" + longitude,
	// function(data){
	// populateStudentTable(data);
	// });
	$.ajax({
		url : "http://localhost:8080/assignment2-gui/api/student/" + student
				+ "/location?latitude=" + latitude + "&longitude=" + longitude,
		type : 'GET',
		dataType : 'json',
		success : function(response) {
			console.log("RESPONSE TO AJAX:" + response)

			populateStudentTable(response);

		}
	});

}

var objectStorage = new Object();

function explodeJSON(object) {
	if (object instanceof Object == true) {
		objectStorage[object['@id']] = object;
		console.log('Object is object');
	} else {
		console.log('Object is not object');
		object = objectStorage[object];
		console.log(object);
	}
	console.log(object);
	return object;
}
// Added for assignment 2 to initialize map
var map;
function initialize_map() {
	var mapOptions = {
		zoom : 10,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	// Try HTML5 geolocation
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = new google.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			map.setCenter(pos);
		}, function() {
			handleNoGeolocation(true);
		});
	} else {
		// Browser doesn't support Geolocation
		// Should really tell the user…
	}
}