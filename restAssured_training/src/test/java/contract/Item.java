
package contract;

import java.util.List;

public class Item {
	private AccessControl accessControl;
	private String category;
	private Number commentCount;
	private Content content;
	private String description;
	private Number duration;
	private String aspectRatio;
	private Number favoriteCount;
	private String id;
	private String likeCount;
	private Player player;
	private Number rating;
	private Number ratingCount;
	private List<Restriction> restrictions;
	private Thumbnail thumbnail;
	private String title;
	private String updated;
	private String uploaded;
	private String uploader;
	private Number viewCount;
	private Status status;

	public AccessControl getAccessControl() {
		return this.accessControl;
	}

	public void setAccessControl(AccessControl accessControl) {
		this.accessControl = accessControl;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Number getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Number commentCount) {
		this.commentCount = commentCount;
	}

	public Content getContent() {
		return this.content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Number getDuration() {
		return this.duration;
	}

	public void setDuration(Number duration) {
		this.duration = duration;
	}

	public Number getFavoriteCount() {
		return this.favoriteCount;
	}

	public void setFavoriteCount(Number favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLikeCount() {
		return this.likeCount;
	}

	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Number getRating() {
		return this.rating;
	}

	public void setRating(Number rating) {
		this.rating = rating;
	}

	public Number getRatingCount() {
		return this.ratingCount;
	}

	public void setRatingCount(Number ratingCount) {
		this.ratingCount = ratingCount;
	}

	public List<Restriction> getRestrictions() {
		return this.restrictions;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public Thumbnail getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getUploaded() {
		return this.uploaded;
	}

	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}

	public String getUploader() {
		return this.uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public Number getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(Number viewCount) {
		this.viewCount = viewCount;
	}

	public String getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
