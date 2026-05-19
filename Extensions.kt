package com.kreedaankana.utils

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kreeda_prefs")

fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Long.toDateString(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toDateTimeString(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

suspend fun DataStore<Preferences>.saveString(key: String, value: String) {
    edit { prefs -> prefs[stringPreferencesKey(key)] = value }
}

suspend fun DataStore<Preferences>.saveBoolean(key: String, value: Boolean) {
    edit { prefs -> prefs[booleanPreferencesKey(key)] = value }
}

fun DataStore<Preferences>.getString(key: String): Flow<String> =
    data.map { it[stringPreferencesKey(key)] ?: "" }

fun DataStore<Preferences>.getBoolean(key: String): Flow<Boolean> =
    data.map { it[booleanPreferencesKey(key)] ?: false }
