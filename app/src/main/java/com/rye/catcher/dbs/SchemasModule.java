package com.rye.catcher.dbs;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {Person.class,Dog.class})
public class SchemasModule {
}
