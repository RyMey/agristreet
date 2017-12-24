<?php

namespace Illuminate\Contracts\Routing;

interface UrlRoutable
{
    /**
     * Get the value of the Model's route key.
     *
     * @return mixed
     */
    public function getRouteKey();

    /**
     * Get the route key for the Model.
     *
     * @return string
     */
    public function getRouteKeyName();
}
