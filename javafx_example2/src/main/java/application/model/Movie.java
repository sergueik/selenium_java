package application.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class Movie {
	private StringProperty title;
	private StringProperty director;
	private IntegerProperty id;
	private ObjectProperty<LocalDate> releaseDate;
	
	public Movie() {
		this(-1, null, null, LocalDate.now());
	}
	
	public Movie(Integer id, String title, String director, LocalDate release_date) {
		this.id.set( id );
		this.title.set( title );
		this.director.set(director );
		this.releaseDate.set( release_date );
	}
	
	// Property Getters
	public StringProperty getTitleProperty() {
		return title;
	}

	public StringProperty getDirectorProperty() {
		return director;
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public ObjectProperty<LocalDate> getReleaseDateProperty() {
		return releaseDate;
	}

	// Normale getter / setter
	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getDirector() {
		return director.get();
	}

	public void setDirector(String director) {
		this.director.set(director);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set( id); 
	}

	public LocalDate getRelease_date() {
		return releaseDate.get();
	}

	public void setReleaseDate(LocalDate release_date) {
		this.releaseDate.set(release_date);
	}
}
