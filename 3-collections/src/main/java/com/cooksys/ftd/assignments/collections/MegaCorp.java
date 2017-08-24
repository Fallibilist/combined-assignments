package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	private Map<Integer, Map<Integer, Capitalist>> mapOfTrees;
	
    public MegaCorp() {
    	mapOfTrees = new HashMap<Integer, Map<Integer, Capitalist>>();
	}

	/**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	if(capitalist == null || has(capitalist)) {
    		return false;
    	} else if(capitalist.hasParent()) {
    		if(has(capitalist.getParent())) {
    			mapOfTrees.get(capitalist.hashFamily()).put(capitalist.hashCode(), capitalist);
    			return true;
    		} else {
    			mapOfTrees.put(capitalist.hashFamily(), new HashMap<Integer, Capitalist>());
    			add(capitalist.getParent());
    			mapOfTrees.get(capitalist.hashFamily()).put(capitalist.hashCode(), capitalist);
    			return true;
    		}
    	} else {
    		if(capitalist instanceof FatCat) {
    			if(mapOfTrees.get(capitalist.hashFamily()) != null) {
    				mapOfTrees.get(capitalist.hashFamily()).put(capitalist.hashCode(), capitalist);
    				return true;
    			} else {
        			mapOfTrees.put(capitalist.hashFamily(), new HashMap<Integer, Capitalist>());
    				mapOfTrees.get(capitalist.hashFamily()).put(capitalist.hashCode(), capitalist);
    				return true;
    			}
    		} else {
    			return false;
    		}
    	}
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	if(mapOfTrees.get(capitalist.hashFamily()) != null){
            return mapOfTrees.get(capitalist.hashFamily()).containsKey(capitalist.hashCode());
    	} else {
    		return false;
    	}
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	Set<Capitalist> setOfCapitalists = new HashSet<Capitalist>();
    	
		(mapOfTrees.values()).forEach(tree -> {
			(tree.values()).forEach(capitalist -> {
				setOfCapitalists.add((Capitalist)capitalist);
	    	});
    	});
    	
        return setOfCapitalists;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> setOfFatCats = new HashSet<FatCat>();
    	
    	getElements().forEach(capitalist -> {
    		if(!(capitalist instanceof WageSlave)) {
    			setOfFatCats.add((FatCat) capitalist);
    		}
    	});
    	
    	return setOfFatCats;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> setOfChildren = new HashSet<Capitalist>();
    	
    	getElements().forEach(potentialChild -> 
		{
			if(potentialChild.hasParent()) {
				if(potentialChild.getParent() == fatCat) {
					setOfChildren.add(potentialChild);
				}
			}
		});
    	return setOfChildren;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	Map<FatCat, Set<Capitalist>> parentChildPairs = new HashMap<FatCat, Set<Capitalist>>();
        
        getParents().forEach(parent -> 
		{
	        parentChildPairs.put(parent, getChildren(parent));
		});
        
        return parentChildPairs;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	List<FatCat> parentChain = new ArrayList<FatCat>();

    	if(capitalist != null) {
	        while(capitalist.hasParent() && has(capitalist.getParent())) {
	        	capitalist = capitalist.getParent();
	        	parentChain.add((FatCat) capitalist);
	        }
    	}
    	return parentChain;
    }
}
