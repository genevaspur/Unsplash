package com.example.unsplash.common

class ListLiveData <E: Any, T: List<E>> (
        value: List<E> = mutableListOf()
) : InitialLiveData<List<E>> (value) {

    fun setElement(values : T) {
        initialValue = values
        super.setValue(initialValue)
    }

    fun postSetElement(values : T) {
        initialValue = values
        super.postValue(initialValue)
    }

    fun setElement(index: Int, value: E) {
        val list = super.initialValue.toMutableList()
        list[index] = value
        initialValue = list as T
        super.setValue(initialValue)
    }

    fun postSetElement(index: Int, value: E) {
        val list = super.initialValue.toMutableList()
        list[index] = value
        initialValue = list as T
        super.postValue(initialValue)
    }

    fun addElement(value: E) {
        val list = super.initialValue.toMutableList()
        list.add(value)
        initialValue = list as T
        super.setValue(initialValue)
    }

    fun addElement(value: T) {
        val list = super.initialValue.toMutableList()
        list.addAll(value)
        initialValue = list as T
        super.setValue(initialValue)
    }

    //리스트에 같은 아이템이 있을 경우 매개변수에 있는 아이템으로 교체한다.
    fun changeElement(value: E) {
        val list = super.initialValue.toMutableList()
        for((index, item) in list.withIndex()) {
            if(item.equals(value)) {
                list.set(index, value)
                break
            }
        }
        initialValue = list as T
        super.setValue(initialValue)
    }

    fun addElement(index: Int, value: E) {
        val list = super.initialValue.toMutableList()
        list.add(index, value)
        initialValue = list as T
        super.postValue(initialValue)
    }

    fun postAddElement(value: E) {
        val list = super.initialValue.toMutableList()
        list.add(value)
        initialValue = list as T
        super.postValue(initialValue)
    }

    fun getElement(index: Int) = initialValue.elementAt(index)
    fun getElementAtOrNull(index: Int) = initialValue.elementAtOrNull(index)

    fun removeAllElement(index : Int) {
        val list = super.initialValue.toMutableList()
        list.removeAt(index)
        initialValue = list as T
        super.postValue(initialValue)
    }

    fun removeAllElement(element: Collection<E>) {
        val list = super.initialValue.toMutableList()
        list.removeAll(element)
        initialValue = list as T
        super.postValue(initialValue)
    }

    fun removeAllElement(block : ((element: E) -> Boolean)) {
        val list = super.initialValue.toMutableList()
        list.removeAll { block(it) }
        initialValue = list as T
        super.postValue(initialValue)
    }

    fun removeAllElement() {
        initialValue = mutableListOf()
        super.postValue(initialValue)
    }

    fun changeIndex(element: E, index: Int) {
        val list = super.initialValue.toMutableList()
        val prevIndex = list.indexOf(element)
        list.removeAt(prevIndex)
        list.add(index, element)
        initialValue = list
        super.postValue(initialValue)
    }

}