<?php

namespace Illuminate\Database\Eloquent;

use RuntimeException;

class ModelNotFoundException extends RuntimeException
{
    /**
     * Name of the affected Eloquent Model.
     *
     * @var string
     */
    protected $model;

    /**
     * Set the affected Eloquent Model.
     *
     * @param  string   $model
     * @return $this
     */
    public function setModel($model)
    {
        $this->model = $model;

        $this->message = "No query results for Model [{$model}].";

        return $this;
    }

    /**
     * Get the affected Eloquent Model.
     *
     * @return string
     */
    public function getModel()
    {
        return $this->model;
    }
}
