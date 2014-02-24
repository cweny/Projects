#include <iostream>
#include "Graph.h"


using namespace std;

int main()
{
    char filename[] = {"labeled_edges.tsv"};

    Graph mygraph(filename);

    vector<string> myvector;
    myvector = mygraph.get_path("FROST, CARMILLA", "KILLRAVEN/JONATHAN R");
    //cout<< "done";
    for(vector<string>::iterator mylist = myvector.begin(); mylist != myvector.end(); mylist++)//iterating through characters
    {
        cout<<*mylist;
    }
    cout<<"\n";
        myvector = mygraph.get_path("GREEN GOBLIN/NORMAN ", "KILLRAVEN/JONATHAN R");
    for(vector<string>::iterator mylist = myvector.begin(); mylist != myvector.end(); mylist++)//iterating through characters
    {
        cout<<*mylist;
    }
    cout<<"\n";
        myvector = mygraph.get_path("HAVOK/ALEX SUMMERS ", "KILLRAVEN/JONATHAN R");
    for(vector<string>::iterator mylist = myvector.begin(); mylist != myvector.end(); mylist++)//iterating through characters
    {
        cout<<*mylist;
    }cout<<"\n";
        myvector = mygraph.get_path("FROST, CARMILLA", "CAGE, LUKE/CARL LUCA");
    for(vector<string>::iterator mylist = myvector.begin(); mylist != myvector.end(); mylist++)//iterating through characters
    {
        cout<<*mylist;
    }cout<<"\n";
        myvector = mygraph.get_path("LYNNE, MONICA", "KILLRAVEN/JONATHAN R");
    for(vector<string>::iterator mylist = myvector.begin(); mylist != myvector.end(); mylist++)//iterating through characters
    {
        cout<<*mylist;
    }
    cin.get();

    return 0;
}
