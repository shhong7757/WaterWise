package com.example.android.waterwise.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.example.android.datastore.SerializedUserProfile
import java.io.InputStream
import java.io.OutputStream

object UserProfileSerializer : Serializer<SerializedUserProfile> {
    override val defaultValue: SerializedUserProfile = SerializedUserProfile.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): SerializedUserProfile {
        try {
            return SerializedUserProfile.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: SerializedUserProfile, output: OutputStream) = t.writeTo(output)
}