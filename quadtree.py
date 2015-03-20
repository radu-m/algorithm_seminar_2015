# coding: utf-8
# Created by Jabok 07-03-2014
import os, sys, random

class Rect:
    def __init__(self, x, y, width, height):
        if type(x) == tuple:
            self.x, self.y = x
            self.width, self.height = y
        else:
            self.x = x
            self.y = y
            self.width = width
            self.height = height
    
    @property
    def right(self):
        return self.x+self.width
    @property
    def left(self):
        return self.x
    @property
    def bottom(self):
        return self.y+self.height
    @property
    def top(self):
        return self.y
    
    def contains(self, item):
        if type(item) == tuple: #point
            return (self.left <= item[0] <= self.right) and (self.top <= item[1] <= self.bottom)
        elif type(item) == Rect:
            if item.left < self.left or item.right > self.right: return False
            if item.top < self.top or item.bottom > self.bottom: return False
            return True
        raise Exception("Unsupported containment check for type '{0}'".format(type(item)))
    
    def colliderect(self, rect):
        if rect.left >= self.right or rect.right <= self.left: return False
        if rect.top >= self.bottom or rect.bottom <= self.top: return False
        return True
    
    def __str__(self):
        return "Rect({0}, {1}, {2}, {3})".format(self.x, self.y, self.width, self.height)
    
# 
class Quad:
    """A quad is a sub-area in a quad tree, containing either objects or subquads"""
    def __init__(self, rect, minsize):
        """Initializes a quad.
        minsize is the smallest acceptable size of a subquad"""
        self.rect = rect
        self.objects = set()
        x, y = rect.x, rect.y
        hw, hh = rect.width/2, rect.height/2
        if hw < minsize[0] or hh < minsize[1]:
            self.final = True
        else:
            self.final = False
            self.nw = Quad(Rect(x   , y   , hw, hh), minsize)
            self.ne = Quad(Rect(x+hw, y   , hw, hh), minsize)
            self.sw = Quad(Rect(x   , y+hh, hw, hh), minsize)
            self.se = Quad(Rect(x+hw, y+hh, hw, hh), minsize)
    
    def subquad_at(self, position):
        """Gets the subquad at the given position"""
        for subquad in {self.nw, self.ne, self.sw, self.se}:
            if subquad.rect.contains(position):
                return subquad
    
    def add(self, obj, position):
        """Adds an object to the quad"""
        if not self.rect.contains(position):
            raise Exception("The position '{0}' for the object '{1}' is outside the bounds of the tree".format(position, obj))
        if self.final:
            self.objects.add(obj)
        else:
            self.subquad_at(position).add(obj, position)
    
    def get(self):
        """Gets all objects"""
        if self.final:
            return self.objects
        else:
            return set().union(self.nw.get(), self.sw.get(), self.se.get(), self.ne.get())
    
    def get_in(self, rect):
        """Gets the object inside the given rect"""
        if self.final:
            return self.objects
        else:
            objs = set()
            for subquad in {self.nw, self.ne, self.sw, self.se}:
                if subquad.rect.colliderect(rect):
                    objs.update(subquad.get_in(rect))
            return objs
    
    def get_at(self, position):
        """Gets a list of objects at the position"""
        if self.final:
            return self.objects
        else:
            return self.subquad_at(position).get_at(position)
    
    def __str__(self):
        return "Quad({0}, [{1}])".format(self.rect, ", ".join([str(obj) for obj in self.objects]))
            

class QuadTree(Quad):
    def __init__(self, rect, minsize):
        super().__init__(rect, minsize)

namestring = "caerowinyloneisaruxalik"
def _get_digram():
    index = random.randint(1, len(namestring)-2)
    return namestring[index:index+2]

def _get_digrams(n):
    s = ""
    for i in range(n):
        s += _get_digram()
    return s
    
def get_name():
    return _get_digrams(random.randint(2, 6)).capitalize()

def main():
    """Entry point"""
    tree = QuadTree(Rect(0,0,10,10), (1,1))
    tree.add("Hello", (0,0))
    tree.add("World", (9,9))
    
    def check_pos(x, y):
        print("({0}, {1}): {2}".format(x, y, tree.get_at((x,y))))
        
    def check_area(x, y, width, height):
        rect = Rect(x, y, width, height)
        print("({0}): {1}".format(rect, tree.get_in(rect)))
    
    for y in range(10):
        for x in range(10):
            tree.add("({0},{1})".format(x, y), (x, y))
    
    
    check_pos(0,0)
    check_pos(9,9)
    check_pos(1,1)
    check_pos(8,8)
    check_area(0,0,5,5)
    check_area(5,0,5,5)
    check_area(0,5,5,5)
    check_area(5,5,5,5)
    #assert(len(tree.get()) == 27)
    print("get:   {0}".format(sorted(tree.get())))
    

if __name__ == '__main__':
    main()
    #print(Rect(5, 0, 5, 5).collideRect(3.75, 5.0, 1.25, 1.25))