package com.example.listlab;

import java.util.LinkedList;

import javax.inject.Inject;
import javax.inject.Singleton;

//////////////////////////////////////////////////////////////////////////
//
//  Singleton class StringList, for creating/managing a single
//  instance of a list of strings.  Note that the type "String"
//  can easily be changed to accommodate a list of whatever
//  kind of objects you wish.  (In such a case, it is a good idea
//  to change the class's name to reflect the objects it holds.)
//
//  Author: M. Halper
//
//////////////////////////////////////////////////////////////////////////

@Singleton
public class StringList extends LinkedList<String>
{
    @Inject
    StringList()
    {
        // default constructor
    }

} // end StringList
