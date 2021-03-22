package com.koleychik.models.fileCarcass.media

import android.os.Parcelable
import com.koleychik.models.fileCarcass.FileCarcass

abstract class MediaCarcass(open val id: Long) : FileCarcass(), Parcelable