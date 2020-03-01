package model.logic;

import java.util.Comparator;

public class OrdenarComparendoLocalidad implements Comparator<Comparendo>
{
	@Override
	public int compare(Comparendo compi1, Comparendo compi2)
	{
		return compi1.darLocalidad().compareTo(compi2.darLocalidad());
	}
}
