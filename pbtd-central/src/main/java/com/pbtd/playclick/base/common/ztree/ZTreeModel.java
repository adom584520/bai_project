
package com.pbtd.playclick.base.common.ztree;

import java.io.Serializable;

public class ZTreeModel implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -8639771415554908548L;

    /**
     * 节点ID
     */
    public Integer id;

    /**
     * 父节点ID
     */
    public Integer pid;

    /**
     * 节点名称
     */
    public String name;

    /**
     * 是否展开
     */
    public Boolean open;

    /**
     * 是否为父节点
     */
    public String isParent;

    /**
     * Radio 是否选中
     */
    public String nocheck;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getPid()
    {
        return pid;
    }

    public void setPid(Integer pid)
    {
        this.pid = pid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Boolean getOpen()
    {
        return open;
    }

    public void setOpen(Boolean open)
    {
        this.open = open;
    }

    public String getIsParent()
    {
        return isParent;
    }

    public void setIsParent(String isParent)
    {
        this.isParent = isParent;
    }

    public String getNocheck()
    {
        return nocheck;
    }

    public void setNocheck(String nocheck)
    {
        this.nocheck = nocheck;
    }
}
