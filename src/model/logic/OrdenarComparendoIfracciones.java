package model.logic;

import java.util.Comparator;

public class OrdenarComparendoIfracciones implements Comparator<Comparendo>
{
	@Override
	public int compare(Comparendo compi1, Comparendo compi2)
	{
		return compi1.darInfraccion().compareTo(compi2.darInfraccion());
	}
}
