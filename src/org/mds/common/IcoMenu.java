package org.mds.common;

/**
 * IcoMenu entity. @author MyEclipse Persistence Tools
 */

public class IcoMenu extends org.king.framework.domain.BaseObject implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String pid;
	private String menuName;
	private String url;
	private String title;
	private String target;
	private String icon;
	private String iconopen;
	private String opened;
	private Integer sortFlag;

	// Constructors

	/** default constructor */
	public IcoMenu() {
	}

	/** minimal constructor */
	public IcoMenu(String id, String pid, String menuName) {
		this.id = id;
		this.pid = pid;
		this.menuName = menuName;
	}

	/** full constructor */
	public IcoMenu(String id, String pid, String menuName, String url,
			String title, String target, String icon, String iconopen,
			String opened, Integer sortFlag) {
		this.id = id;
		this.pid = pid;
		this.menuName = menuName;
		this.url = url;
		this.title = title;
		this.target = target;
		this.icon = icon;
		this.iconopen = iconopen;
		this.opened = opened;
		this.sortFlag = sortFlag;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconopen() {
		return this.iconopen;
	}

	public void setIconopen(String iconopen) {
		this.iconopen = iconopen;
	}

	public String getOpened() {
		return this.opened;
	}

	public void setOpened(String opened) {
		this.opened = opened;
	}

	public Integer getSortFlag() {
		return this.sortFlag;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null && arg0 instanceof IcoMenu)
		{
			IcoMenu im = (IcoMenu)arg0;
			return im.getId().equals(id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + menuName;
	}

}